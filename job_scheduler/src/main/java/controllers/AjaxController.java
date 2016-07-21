package main.java.controllers;

import com.mysql.jdbc.SQLError;
import main.java.dao.model.OrderDAO;
import main.java.dao.model.ProductOperationDAO;
import main.java.dao.model.UserDAO;
import main.java.model.*;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/data")
public class AjaxController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductOperationDAO productOperationDAO;

    @Autowired
    private UserDAO userDAO;

//    @Autowired
//    private JdbcOrderDAO jdbcOrderDAO;
//
//    @Autowired
//    private JdbcJobDAO jdbcJobDAO;
//
//    @Autowired
//    private JdbcResourceDAO jdbcResourceDAO;
//
//    @Autowired
//    private JdbcItemDAO jdbcItemDAO;
//
//    @Autowired
//    private JdbcTaskDAO jdbcTaskDAO;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody void newOrder(@RequestBody Order order,
                                       HttpServletResponse response) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        if(userDAO.hasPermission(order, user)) {
            orderDAO.insert(order);
        }
        else {
            response.setStatus(401);
        }
    }

//    @RequestMapping(value = "/neworder", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    VisContent newOrder(@RequestBody Order order) throws ParseException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        String colors[] = {"red", "gold", "magenta", "red", "grey", "blue", "lightpink"};
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date parsedDate = formatter.parse(order.getStartDate());
//        order.setStartDate(formater.format(parsedDate));
//        int orderId = jdbcOrderDAO.createNewOrder(order, name);
//        Map<Integer, Date> endDates = new HashMap<Integer, Date>();
//        Map<Integer, Date> startDates = new HashMap<Integer, Date>();
//        Item item = new Item();
//        for (Job job : order.getJobs()) {
//            for (Task task : job.getTasks()) {
//                if (!endDates.containsKey(task.getResourceId())) {
//                    endDates.put(task.getResourceId(), parsedDate);
//                }
//            }
//        }
//        int i = 1;
//        for (Job job : order.getJobs()) {
//            for (Task task : job.getTasks()) {
//                startDates.put(task.getResourceId(), parsedDate);
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(parsedDate);
//                calendar.add(Calendar.SECOND, task.getSecondsDuration());
//                parsedDate = calendar.getTime();
//            }
//            Long diff = Long.MAX_VALUE;
//            for (Map.Entry<Integer, Date> entry : endDates.entrySet()) {
//                if (startDates.containsKey(entry.getKey())) {
//                    Long diffTmp = startDates.get(entry.getKey()).getTime() - entry.getValue().getTime();
//                    if (diffTmp < diff) {
//                        diff = diffTmp;
//                    }
//                }
//            }
//            for (Task task : job.getTasks()) {
//                parsedDate = startDates.get(task.getResourceId());
//                parsedDate = item.convertFromTask(task, job, parsedDate, diff);
//                endDates.put(task.getResourceId(), parsedDate);
//                item.setColor(colors[item.getJob().getJobId() % 6]);
//                jdbcItemDAO.createNewItem(item, orderId, i);
//                i++;
//            }
//            startDates.clear();
//        }
//        return new VisContent(jdbcItemDAO.getOrderItems(orderId),
//                jdbcResourceDAO.getOrderGroups(orderId),
//                jdbcOrderDAO.getOrder(orderId));
//    }

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
    void deleteResource(HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        System.out.println("spring: " + name);
//        User user = userDAO.getUserByLogin(name);
//        System.out.println("imię: " + user.getFirstName());
//        Group group = new Group();
//        group.setGroupId(1);
//        Product product = new Product();
//        ResourceType resourceType = new ResourceType();
//        ProductOperation productOperation = new ProductOperation();
//        product.setId(1);
//        product.setName("rower");
//        product.setDescription("rower szosowy");
//        resourceType.setId(1);
//        resourceType.setName("tokarki");
//        productOperation.setName("spring");
//        productOperation.setDescription("test JPA");
//        productOperation.setDuration(10);
//        productOperation.setProduct(product);
//        productOperation.setResourceType(resourceType);
//        productOperation.setGroup(group);
//        productOperationDAO.insert(productOperation);
//        System.out.println("id: " + productOperation.getId());
       // personDao.sayHey();
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
        productOperation.setDuration(10);
        productOperation.setProduct(product);
        productOperation.setResourceType(resourceType);
        productOperation.setGroup(group);
        productOperationDAO.insert(productOperation);
    }
}