package main.java.controllers;

import main.java.model.*;
import main.java.modules.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful API controller. It handles remove requests
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/remove")
public class RemoveController {

    @Autowired
    private ProductsModule productsModule;

    @Autowired
    private OrdersModule ordersModule;

    @Autowired
    private ResourcesModule resourcesModule;

    @Autowired
    private ProductionPlansModule productionPlansModule;

    @Autowired
    private UsersModule usersModule;
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public @ResponseBody void item(@RequestBody Item item) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        productionPlansModule.removeItem(item, user);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public @ResponseBody Integer order(@PathVariable int orderId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return ordersModule.removeOrder(orderId, user);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public @ResponseBody List<Integer> orders(@RequestBody List<Integer> orderIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return ordersModule.removeOrders(orderIds, user);
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public @ResponseBody Integer product(@PathVariable int productId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productsModule.removeProduct(productId, user);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public @ResponseBody List<Integer> products(@RequestBody List<Integer> productIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productsModule.removeProducts(productIds, user);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody void productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        productionPlansModule.removeProductionPlan(productionPlan, user);
    }

    @RequestMapping(value = "/resource/{resourceId}", method = RequestMethod.GET)
    public @ResponseBody Integer resource(@PathVariable int resourceId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.removeResource(resourceId, user);
    }

    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    public @ResponseBody List<Integer> resources(@RequestBody List<Integer> resourceIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.removeResources(resourceIds, user);
    }

    @RequestMapping(value = "/resourcetype/{resourceTypeId}", method = RequestMethod.GET)
    public @ResponseBody Integer resourceType(@PathVariable int resourceTypeId) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.removeResourceType(resourceTypeId, user);
    }

    @RequestMapping(value = "/resourcetypes", method = RequestMethod.POST)
    public @ResponseBody List<Integer> resourceTypes(@RequestBody List<Integer> resourceTypeIds) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.removeResourceTypes(resourceTypeIds, user);
    }
}
