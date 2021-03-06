package main.java.modules;

import main.java.dao.model.ItemDAO;
import main.java.dao.model.ProductionPlanDAO;
import main.java.dao.model.UserDAO;
import main.java.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Module responsible for Production plans processing
 * @see main.java.model.ProductionPlan
 * @author Marcin Frankowski
 */
public class ProductionPlansModule {
    /**
     * Production Plan Data Access Objects
     */
    private ProductionPlanDAO productionPlanDAO;

    /**
     * Item Data Access Objects
     */
    private ItemDAO itemDAO;

    /**
     * User Data Access Objects
     */
    private UserDAO userDAO;

    /**
     * Default constructor
     */
    public ProductionPlansModule() {}

    /**
     * Constructor which sets Production Plan DAO, Item DAO and User DAO.
     * Module must be initialized with this constructor to work properlly.
     * @param productionPlanDAO Production Plan Data Access Object
     * @param itemDAO Item Data Access Object
     * @param userDAO User Data Access Object
     */
    public ProductionPlansModule(ProductionPlanDAO productionPlanDAO, ItemDAO itemDAO, UserDAO userDAO) {
        this.productionPlanDAO = productionPlanDAO;
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }

    /**
     * Save new or existing Production Plan in system.
     * @param productionPlan production plan to save
     * @param user user who wants to save production plan
     * @return saved production plan
     */
    public ProductionPlan saveProductionPlan(ProductionPlan productionPlan, User user) {
        userDAO.confirmPermission(ProductionPlan.class, productionPlan.getId(), user);
        productionPlan.setGroupId(user.getGroupId());
        ProductionPlan mergePlan = productionPlanDAO.save(productionPlan);
        process(mergePlan);
        itemDAO.removeByPlanId(mergePlan.getId());
        for(Item item : mergePlan.getItems()) {
            itemDAO.save(item);
        }
        return mergePlan;
    }

    /**
     * Get all production plans to which user has permission.
     * @param user user who wants to get production plans
     * @return List of user's production plans
     */
    public List<ProductionPlan> getUserProductionPlans(User user) {
        return productionPlanDAO.getUsersProductionPlans(user);
    }

    /**
     * Get specific production plan with given id. It checks if user has permission for this production plan.
     * @param productionPlanId production plan id
     * @param user user who wants to get production plan
     * @return Production plan with given id
     */
    public ProductionPlan getProductionPlan(Integer productionPlanId, User user) {
        userDAO.confirmPermission(ProductionPlan.class, productionPlanId, user);
        return productionPlanDAO.getProductionPlan(productionPlanId);
    }

    /**
     * Remove production plan from system.
     * @param productionPlan production plan to remove
     * @param user user who wants to remove production plan
     */
    public void removeProductionPlan(ProductionPlan productionPlan, User user) {
        userDAO.confirmPermission(ProductionPlan.class, productionPlan.getId(), user);
        productionPlanDAO.remove(productionPlan);
    }

    /**
     * Save new or existing Item in system.
     * @param item Item to save
     * @param user user who wants to save item
     * @return saved item
     */
    public Item saveItem(Item item, User user) {
        userDAO.confirmPermission(Item.class, item.getId(), user);
        itemDAO.save(item);
        return item;
    }

    /**
     * Remove item from system.
     * @param item item to remove
     * @param user user who wants to remove item
     */
    public void removeItem(Item item, User user) {
        userDAO.confirmPermission(Item.class, item.getId(), user);
        itemDAO.remove(item);
    }

    /**
     * this function creates list of items with their position on timeline.
     * It get each product from production plan's order and sequentially creates item fo each product operation
     * There is no scheduling. Items are in product's order.
     * Items are set in productionPlan.
     * @param productionPlan production plan to process
     */
    public void process(ProductionPlan productionPlan) {
        String[] classNames = {"red", "orange", "magneta", "blue", "green", "grey", "pink"};
        int index = 0;
        LocalDateTime dateTime = productionPlan.getStart();
        List<Item> items = new LinkedList<>();
        Map<Integer, LocalDateTime> endDates = new HashMap<>();
        Map<Integer, LocalDateTime> startDates = new HashMap<>();
        Map<Integer, LocalDateTime> endDates2 = new HashMap<>();
        List<Integer> resourceIds = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for(Order order : productionPlan.getOrders()) {
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                for(int i = 0; i < orderProduct.getAmount(); i++) {
                    products.add(orderProduct.getProduct());
                }
            }
        }
        for(Product product : products) {
            resourceIds.addAll(product.getProductOperations().stream().map(ProductOperation::getResourceId).collect(Collectors.toList()));
        }
        Set<Integer> uniqueResourceIds = new HashSet<>(resourceIds);
        for(Integer id : uniqueResourceIds) {
            endDates2.put(id, productionPlan.getStart());
        }
        for(Product product : products) {
            List<Item> tempItems = new LinkedList<>();
            for(ProductOperation productOperation : product.getProductOperations()) {
                if(productOperation.getDuration().getSeconds() > 0) {
                    startDates.put(productOperation.getResourceId(), dateTime);
                    dateTime = dateTime.plus(productOperation.getDuration());
                    endDates.put(productOperation.getResourceId(), dateTime);
                    tempItems.add(new Item(startDates.get(productOperation.getResourceId()),
                            endDates.get(productOperation.getResourceId()),
                            classNames[index % classNames.length],
                            productOperation.getResourceId(),
                            product.getId(),
                            productionPlan.getId(),
                            productionPlan.getGroupId()));
                }
            }
            long diff = Long.MAX_VALUE;
            for (Map.Entry<Integer, LocalDateTime> entry : endDates2.entrySet()) {
                if (startDates.containsKey(entry.getKey())) {
                    long diffTmp = Duration.between(entry.getValue(), startDates.get(entry.getKey())).toNanos();
                    if (diffTmp < diff) {
                        diff = diffTmp;
                    }
                }
            }
            for(Item item : tempItems) {
                item.setStart(item.getStart().minusNanos(diff));
                item.setEnd(item.getEnd().minusNanos(diff));
                endDates2.put(item.getResourceId(), item.getEnd());
            }
            items.addAll(tempItems);
            tempItems.clear();
            startDates.clear();
            endDates.clear();
            index++;
        }
        productionPlan.setItems(items);
    }


    /**
     * Like process function, but it puts item in first free gap in timeline
     * @param productionPlan production plan to process
     */
    public void fillGaps(ProductionPlan productionPlan) {
        String[] classNames = {"red", "orange", "magneta", "blue", "green", "grey", "pink"};
        Map<Integer, SortedSet<ProductionPlansModule.TimelineGap>> timelineGaps = new HashMap<>();
        int index = 0;
        List<Integer> resourceIds = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for(Order order : productionPlan.getOrders()) {
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                for(int i = 0; i < orderProduct.getAmount(); i++) {
                    products.add(orderProduct.getProduct());
                }
            }
        }
        for(Product product : products) {
            resourceIds.addAll(product.getProductOperations().stream().map(ProductOperation::getResourceId).collect(Collectors.toList()));
        }
        Set<Integer> uniqueResourceIds = new HashSet<>(resourceIds);
        SortedSet resTimelineGaps = new TreeSet<>();
        resTimelineGaps.add(new ProductionPlansModule.TimelineGap(productionPlan.getStart(), LocalDateTime.MAX));
        for(Integer id : uniqueResourceIds) {
            timelineGaps.put(id, resTimelineGaps);
        }
        for(Product product : products) {
            String className = classNames[index % classNames.length];
            LocalDateTime prevOperationEnd = productionPlan.getStart();
            for(ProductOperation productOperation : product.getProductOperations()) {
                if (productOperation.getDuration().getSeconds() > 0) {
                    Iterator it = timelineGaps.get(productOperation.getResourceId()).iterator();
                    List<ProductionPlansModule.TimelineGap> newGaps = new LinkedList<>();
                    while(it.hasNext()) {
                        ProductionPlansModule.TimelineGap timelineGap = (ProductionPlansModule.TimelineGap)it.next();
                        if((prevOperationEnd.isAfter(timelineGap.getStartTime()) || prevOperationEnd.isEqual(timelineGap.getStartTime()))
                                && prevOperationEnd.isBefore(timelineGap.getEndTime())
                                && Duration.between(prevOperationEnd, timelineGap.getEndTime()).toNanos() >= productOperation.getDuration().toNanos()) {
                            LocalDateTime itemStart = prevOperationEnd;
                            LocalDateTime itemEnd = itemStart.plus(productOperation.getDuration());

                            productionPlan.getItems().add(new Item(itemStart,
                                    itemEnd,
                                    className,
                                    productOperation.getResourceId(),
                                    product.getId(),
                                    productionPlan.getId(),
                                    productionPlan.getGroupId()));

                            if(timelineGap.getStartTime().isEqual(prevOperationEnd)) {
                                newGaps.add(new ProductionPlansModule.TimelineGap(itemEnd,timelineGap.getEndTime()));
                            }
                            else {
                                newGaps.add(new ProductionPlansModule.TimelineGap(timelineGap.getStartTime(), itemStart));
                                newGaps.add(new ProductionPlansModule.TimelineGap(itemEnd,timelineGap.getEndTime()));
                            }
                            it.remove();
                            break;
                        }
                    }
                    for(ProductionPlansModule.TimelineGap timelineGap : newGaps) {
                        timelineGaps.get(productOperation.getResourceId()).add(timelineGap);
                    }
                }
            }
            index++;
        }
    }

    /**
     * Help class for fillGaps function. 
     * Should be moved outside of module
     */

    /*
     * TODO move outside
     */
    private class TimelineGap implements Comparable<ProductionPlansModule.TimelineGap>{
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public TimelineGap(){}

        public TimelineGap(LocalDateTime startTime, LocalDateTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        @Override
        public int compareTo(ProductionPlansModule.TimelineGap timelineGap) {
            return startTime.compareTo(timelineGap.getStartTime());
        }
    }
}
