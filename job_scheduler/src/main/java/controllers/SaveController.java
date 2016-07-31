package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/save")
public class SaveController {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductionPlanDAO productionPlanDAO;

    @Autowired
    private ProductOperationDAO productOperationDAO;

    @Autowired
    private ResourceDAO resourceDAO;

    @Autowired
    private ResourceTypeDAO resourceTypeDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public @ResponseBody void item(@RequestBody Item item) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(item, user);
        itemDAO.insert(item);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody void order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(order, user);
        orderDAO.insert(order);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public @ResponseBody void product(@RequestBody Product product) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(product, user);
        productDAO.insert(product);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody List<Item> productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(productionPlan, user);
        productionPlan.process();
        productionPlanDAO.insert(productionPlan);
        return new ArrayList<>(productionPlan.getItems());
    }

    @RequestMapping(value = "/productoperation", method = RequestMethod.POST)
    public @ResponseBody void productOperation(@RequestBody ProductOperation productOperation) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(productOperation, user);
        productOperationDAO.insert(productOperation);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public @ResponseBody void resource(@RequestBody Resource resource) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(resource, user);
        resourceDAO.insert(resource);
    }

    @RequestMapping(value = "/resourcetype", method = RequestMethod.POST)
    public @ResponseBody void resourcetype(@RequestBody ResourceType resourceType) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(resourceType, user);
        resourceTypeDAO.insert(resourceType);
    }

    @RequestMapping(value = "/localdatetime", method = RequestMethod.POST)
    public @ResponseBody void resourcetype(@RequestBody LocalDateTime localDateTime) throws ParseException {
        System.out.println(localDateTime);
    }


//    @RequestMapping(value = "/addjob", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    VisContent addJob(@RequestBody Job job) throws ParseException {
//        String colors[] = {"red", "gold", "magneta", "red", "grey", "blue", "lightpink"};
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        Map<Integer, Date> endDates = jdbcItemDAO.getEndDates(name);
//        Map<Integer, Date> startDates = new HashMap<Integer, Date>();
//        Date parsedDate = jdbcItemDAO.getMaxDate(name);
//        Date startDate = jdbcOrderDAO.getStartDate(name);
//        Item item = new Item();
//        int orderId = jdbcOrderDAO.getOrderInUseId(name);
//
//        for (Task task : job.getTasks()) {
//            if (!endDates.containsKey(task.getResourceId())) {
//                endDates.put(task.getResourceId(), startDate);
//            }
//        }
//
//        for (Task task : job.getTasks()) {
//            startDates.put(task.getResourceId(), parsedDate);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(parsedDate);
//            calendar.add(Calendar.SECOND, task.getSecondsDuration());
//            parsedDate = calendar.getTime();
//        }
//
//        Long diff = Long.MAX_VALUE;
//        for (Map.Entry<Integer, Date> entry : endDates.entrySet()) {
//            if (startDates.containsKey(entry.getKey())) {
//                Long diffTmp = startDates.get(entry.getKey()).getTime() - entry.getValue().getTime();
//                if (diffTmp < diff) {
//                    diff = diffTmp;
//                }
//            }
//        }
//        for (Task task : job.getTasks()) {
//            parsedDate = startDates.get(task.getResourceId());
//            parsedDate = item.convertFromTask(task, job, parsedDate, diff);
//            endDates.put(task.getResourceId(), parsedDate);
//            item.setColor(colors[item.getJob().getJobId() % 6]);
//            jdbcItemDAO.createNewItem(item, orderId);
//        }
//        return new VisContent(jdbcItemDAO.getOrderItems(orderId),
//                jdbcResourceDAO.getOrderGroups(orderId),
//                jdbcOrderDAO.getOrder(orderId));
//    }

//    @RequestMapping(value = "/newresource", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void newResource(@RequestBody Resource resource) throws ParseException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcResourceDAO.createNewResource(resource, name);
//    }

//    @RequestMapping(value = "/updateorder", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    VisContent updateOrder(@RequestBody Order order) throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parsedDate = formatter.parse(order.getStartDate());
//        order.setStartDate(formater.format(parsedDate));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcOrderDAO.updateOrder(order, name);
//        return new VisContent(jdbcItemDAO.getOrderItems(order.getOrderId()),
//                jdbcResourceDAO.getOrderGroups(order.getOrderId()),
//                jdbcOrderDAO.getOrder(order.getOrderId()));
//    }
//
//    @RequestMapping(value = "/newjob", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void newJob(@RequestBody Job job) throws ParseException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        int jobId = jdbcJobDAO.createNewJob(job, name);
//        int i = 1;
//        for (Task task : job.getTasks()) {
//            try {
//                task.convertTimeToSeconds();
//                jdbcTaskDAO.createNewTask(task, jobId, i);
//                i++;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @RequestMapping(value = "/deletejob", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void deleteJob(@RequestBody Job job) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcJobDAO.deleteJob(job, name);
//    }
//
//    @RequestMapping(value = "/updatejob", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void updateJob(@RequestBody Job job) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcJobDAO.updateJob(job, name);
//    }
//
//    @RequestMapping(value = "/deleteresource", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void deleteResource(@RequestBody Resource resource) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcResourceDAO.deleteResource(resource, name);
//    }
//
//    @RequestMapping(value = "/updateresource", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void updateResource(@RequestBody Resource resource) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcResourceDAO.updateResource(resource, name);
//    }
//
//    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void updateItem(@RequestBody VisItem item) throws ParseException {
//        System.out.println(item.getStart());
//        System.out.println(item.getEnd());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startDate = formatter.parse(item.getStart());
//        Date endDate = formatter.parse(item.getEnd());
//        item.setStart(formater.format(startDate));
//        item.setEnd(formater.format(endDate));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        jdbcItemDAO.updateItem(item, name);
//    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteResource() {
        Group group = new Group();
        group.setGroupId(1);
        Product product = new Product();
        ResourceType resourceType = new ResourceType();
        ProductOperation productOperation = new ProductOperation();
        product.setId(1);
        product.setName("test");
        product.setDescription("test");
        resourceType.setId(1);
        resourceType.setName("test");
        productOperation.setDescription("test");
        productOperation.setName("test");
        productOperation.setDuration(10);
        productOperation.setProductId(1);
        productOperation.setResourceTypeId(1);
        productOperation.setGroupId(1);
        //productOperationDAO.insert(productOperation);
    }
}
