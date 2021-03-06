package application.rest;

import application.domain.Menu;
import application.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/menu")
@RestController
public class MenuRestController {

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "10";

    @Autowired
    private MenuService service;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void upload(@RequestBody List<Menu> menus) {
        service.saveMenusBulk(menus);
    }

    @RequestMapping(value = "/bulk",method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody Menu menu) {
        service.saveMenu(menu);
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public Menu getLatest(@PathVariable("restaurantId") String restaurantId) {
        System.out.println(restaurantId);
        return service.getLatestMenu(Long.parseLong(restaurantId));
    }

    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET)
    public Page<Menu> getHistoryMenus(@PathVariable("restaurantId") Long restaurantId,
                                      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(name = "size", required = false, defaultValue = DEFAULT_SIZE) int size) {
        return service.getHistoryMenuByRestaurant(restaurantId, new PageRequest(page, size));
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
    public Menu getMenu(@PathVariable("menuId") Long menuId) {
        return service.getMenu(menuId);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
    public void deleteMenu(@PathVariable("menuId") Long menuId) {
        service.deleteMenu(menuId);
    }


}
