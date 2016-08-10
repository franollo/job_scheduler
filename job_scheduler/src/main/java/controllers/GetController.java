package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/get")
public class GetController {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductionPlanDAO productionPlanDAO;

    @Autowired
    private ResourceTypeDAO resourceTypeDAO;

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public @ResponseBody List<Order> orders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        return orderDAO.getUsersOrders(user);
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public @ResponseBody List<ResourceType> resources() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        return resourceTypeDAO.getUsersResourceTypes(user);
    }

    @RequestMapping(value = "/productionplans", method = RequestMethod.GET)
    public @ResponseBody List<ProductionPlan> productionPlans() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        return productionPlanDAO.getUsersProductionPlans(user);
    }

    @RequestMapping(value = "/productionplan/{productionPlanId}", method = RequestMethod.GET)
    public @ResponseBody ProductionPlan productionPlan(@PathVariable int productionPlanId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.confirmPermission(ProductionPlan.class, productionPlanId, user);
        return productionPlanDAO.getProductionPlan(productionPlanId);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> products() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        return productDAO.getUsersProducts(user);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public @ResponseBody User userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.getUserByLogin(auth.getName());
    }





//    @Autowired
//    private JdbcResourceDAO resourceDAO;
//
//    @Autowired
//    private JdbcJobDAO jobDAO;
//
//    @Autowired
//    private JdbcTaskDAO taskDAO;
//
//    @Autowired
//    private JdbcOrderDAO orderDAO;
//
//    @Autowired
//    private JdbcItemDAO itemDAO;

//    @RequestMapping(value = "/orders", method = RequestMethod.GET)
//    public List<Order> getOrders() {
//        System.out.println("Debug Message from /orders");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        return orderDAO.getUserOrders(name);
//    }
//
//    @RequestMapping(value = "/resources", method = RequestMethod.GET)
//    public List<Resource> getResources() {
//        System.out.println("Debug Message from /resources");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        System.out.println(name);
//        return resourceDAO.getUserResources(name);
//    }
//
//    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
//    public List<Job> getJobs() {
//        System.out.println("Debug Message from /jobs");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        List<Job> jobs = jobDAO.getUserJobs(name);
//        for (Job job : jobs) {
//            List<Task> tasks = taskDAO.getTasksByJobId(job.getJobId());
//            for (Task task : tasks) {
//                task.setResource(resourceDAO.getByID(task.getResourceId()));
//            }
//            job.setTasks(tasks);
//        }
//        return jobs;
//    }
//
////    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
////    public ResponseEntity<UserOld> getUser() {
////        System.out.println("Debug Message from /userinfo");
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        String name = SecurityContextHolder.getContext().getAuthentication().getName();
////        HttpStatus status = name != "anonymousUser" ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
////        return new ResponseEntity<UserOld>(userDAO.getUserByLogin(name), status);
////    }
//
//    @RequestMapping(value = "/order", method = RequestMethod.POST)
//    public VisContent getOrder(@RequestBody int orderId) {
//        System.out.println("Debug Message from /order");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//       // userDAO.setOrderInUse(name, orderId);
//        return new VisContent(itemDAO.getOrderItems(orderId),
//                resourceDAO.getOrderGroups(orderId),
//                orderDAO.getOrder(orderId));
//    }
}

