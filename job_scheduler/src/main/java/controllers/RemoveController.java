package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/remove")
public class RemoveController {

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
        itemDAO.delete(item);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody void order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(order, user);
        orderDAO.delete(order);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public @ResponseBody void product(@RequestBody Product product) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(product, user);
        productDAO.delete(product);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody void productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(productionPlan, user);
        productionPlanDAO.delete(productionPlan);
    }

    @RequestMapping(value = "/productoperation", method = RequestMethod.POST)
    public @ResponseBody void productOperation(@RequestBody ProductOperation productOperation) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(productOperation, user);
        productOperationDAO.delete(productOperation);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public @ResponseBody void resource(@RequestBody Resource resource) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(resource, user);
        resourceDAO.delete(resource);
    }

    @RequestMapping(value = "/resourcetype", method = RequestMethod.POST)
    public @ResponseBody void resourcetype(@RequestBody ResourceType resourceType) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDAO.getUserByLogin(auth.getName());
        userDAO.hasPermission(resourceType, user);
        resourceTypeDAO.delete(resourceType);
    }
}
