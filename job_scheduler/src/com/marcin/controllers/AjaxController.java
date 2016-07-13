package com.marcin.controllers;

import com.marcin.dao.PersonDao;
import com.marcin.dao.ProductOperationDAO;
import com.marcin.dao.implementation.*;
import com.marcin.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marcin Frankowski
 */

@Controller
@RequestMapping("/data")
public class AjaxController {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private ProductOperationDAO productOperationDAO;

    @Autowired
    private JdbcOrderDAO jdbcOrderDAO;

    @Autowired
    private JdbcJobDAO jdbcJobDAO;

    @Autowired
    private JdbcResourceDAO jdbcResourceDAO;

    @Autowired
    private JdbcItemDAO jdbcItemDAO;

    @Autowired
    private JdbcTaskDAO jdbcTaskDAO;

    @Autowired
    private HibernateUserDAO userDAO;

    @RequestMapping(value = "/neworder", method = RequestMethod.POST)
    public
    @ResponseBody
    VisContent newOrder(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        String colors[] = {"red", "gold", "magenta", "red", "grey", "blue", "lightpink"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = formatter.parse(order.getStartDate());
        order.setStartDate(formater.format(parsedDate));
        int orderId = jdbcOrderDAO.createNewOrder(order, name);
        Map<Integer, Date> endDates = new HashMap<Integer, Date>();
        Map<Integer, Date> startDates = new HashMap<Integer, Date>();
        Item item = new Item();
        for (Job job : order.getJobs()) {
            for (Task task : job.getTasks()) {
                if (!endDates.containsKey(task.getResourceId())) {
                    endDates.put(task.getResourceId(), parsedDate);
                }
            }
        }
        int i = 1;
        for (Job job : order.getJobs()) {
            for (Task task : job.getTasks()) {
                startDates.put(task.getResourceId(), parsedDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                calendar.add(Calendar.SECOND, task.getSecondsDuration());
                parsedDate = calendar.getTime();
            }
            Long diff = Long.MAX_VALUE;
            for (Map.Entry<Integer, Date> entry : endDates.entrySet()) {
                if (startDates.containsKey(entry.getKey())) {
                    Long diffTmp = startDates.get(entry.getKey()).getTime() - entry.getValue().getTime();
                    if (diffTmp < diff) {
                        diff = diffTmp;
                    }
                }
            }
            for (Task task : job.getTasks()) {
                parsedDate = startDates.get(task.getResourceId());
                parsedDate = item.convertFromTask(task, job, parsedDate, diff);
                endDates.put(task.getResourceId(), parsedDate);
                item.setColor(colors[item.getJob().getJobId() % 6]);
                jdbcItemDAO.createNewItem(item, orderId, i);
                i++;
            }
            startDates.clear();
        }
        return new VisContent(jdbcItemDAO.getOrderItems(orderId),
                jdbcResourceDAO.getOrderGroups(orderId),
                jdbcOrderDAO.getOrder(orderId));
    }

    @RequestMapping(value = "/addjob", method = RequestMethod.POST)
    public
    @ResponseBody
    VisContent addJob(@RequestBody Job job) throws ParseException {
        String colors[] = {"red", "gold", "magneta", "red", "grey", "blue", "lightpink"};
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Map<Integer, Date> endDates = jdbcItemDAO.getEndDates(name);
        Map<Integer, Date> startDates = new HashMap<Integer, Date>();
        Date parsedDate = jdbcItemDAO.getMaxDate(name);
        Date startDate = jdbcOrderDAO.getStartDate(name);
        Item item = new Item();
        int orderId = jdbcOrderDAO.getOrderInUseId(name);

        for (Task task : job.getTasks()) {
            if (!endDates.containsKey(task.getResourceId())) {
                endDates.put(task.getResourceId(), startDate);
            }
        }

        for (Task task : job.getTasks()) {
            startDates.put(task.getResourceId(), parsedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.add(Calendar.SECOND, task.getSecondsDuration());
            parsedDate = calendar.getTime();
        }

        Long diff = Long.MAX_VALUE;
        for (Map.Entry<Integer, Date> entry : endDates.entrySet()) {
            if (startDates.containsKey(entry.getKey())) {
                Long diffTmp = startDates.get(entry.getKey()).getTime() - entry.getValue().getTime();
                if (diffTmp < diff) {
                    diff = diffTmp;
                }
            }
        }
        for (Task task : job.getTasks()) {
            parsedDate = startDates.get(task.getResourceId());
            parsedDate = item.convertFromTask(task, job, parsedDate, diff);
            endDates.put(task.getResourceId(), parsedDate);
            item.setColor(colors[item.getJob().getJobId() % 6]);
            jdbcItemDAO.createNewItem(item, orderId);
        }
        return new VisContent(jdbcItemDAO.getOrderItems(orderId),
                jdbcResourceDAO.getOrderGroups(orderId),
                jdbcOrderDAO.getOrder(orderId));
    }

    @RequestMapping(value = "/newresource", method = RequestMethod.POST)
    public
    @ResponseBody
    void newResource(@RequestBody Resource resource) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcResourceDAO.createNewResource(resource, name);
    }

    @RequestMapping(value = "/updateorder", method = RequestMethod.POST)
    public
    @ResponseBody
    VisContent updateOrder(@RequestBody Order order) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = formatter.parse(order.getStartDate());
        order.setStartDate(formater.format(parsedDate));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcOrderDAO.updateOrder(order, name);
        return new VisContent(jdbcItemDAO.getOrderItems(order.getOrderId()),
                jdbcResourceDAO.getOrderGroups(order.getOrderId()),
                jdbcOrderDAO.getOrder(order.getOrderId()));
    }

    @RequestMapping(value = "/newjob", method = RequestMethod.POST)
    public
    @ResponseBody
    void newJob(@RequestBody Job job) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        int jobId = jdbcJobDAO.createNewJob(job, name);
        int i = 1;
        for (Task task : job.getTasks()) {
            try {
                task.convertTimeToSeconds();
                jdbcTaskDAO.createNewTask(task, jobId, i);
                i++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/deletejob", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteJob(@RequestBody Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcJobDAO.deleteJob(job, name);
    }

    @RequestMapping(value = "/updatejob", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateJob(@RequestBody Job job) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcJobDAO.updateJob(job, name);
    }

    @RequestMapping(value = "/deleteresource", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteResource(@RequestBody Resource resource) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcResourceDAO.deleteResource(resource, name);
    }

    @RequestMapping(value = "/updateresource", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateResource(@RequestBody Resource resource) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcResourceDAO.updateResource(resource, name);
    }

    @RequestMapping(value = "/updateitem", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateItem(@RequestBody VisItem item) throws ParseException {
        System.out.println(item.getStart());
        System.out.println(item.getEnd());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse(item.getStart());
        Date endDate = formatter.parse(item.getEnd());
        item.setStart(formater.format(startDate));
        item.setEnd(formater.format(endDate));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        jdbcItemDAO.updateItem(item, name);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteResource(@RequestBody Person person) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userDAO.getUserByLogin(name);
//        Product product = new Product();
//        ResourceType resourceType = new ResourceType();
//        ProductOperation productOperation = new ProductOperation();
//        product.setProductId(1);
//        product.setName("rower");
//        product.setDescription("rower szosowy");
//        resourceType.setResourceTypeId(1);
//        resourceType.setName("tokarki");
//        productOperation.setName("spring");
//        productOperation.setDescription("test JPA");
//        productOperation.setDuration(10);
//        productOperation.setProduct(product);
//        productOperation.setResourceType(resourceType);
//        productOperationDAO.insert(productOperation);
       // personDao.sayHey();
    }
}
