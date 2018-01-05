package org.roof.web.menu.action;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.roof.spring.Result;
import org.roof.web.core.TreeSelectDate;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuDto;
import org.roof.web.menu.entity.MenuType;
import org.roof.web.menu.entity.MenuVo;
import org.roof.web.menu.service.api.IMenuService;
import org.roof.web.resource.entity.ResourceVo;
import org.roof.web.resource.service.api.IResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("base/menu")
public class MenuAction {
	private IMenuService menuService;
	@Autowired
	private IResourceService resourceService;

	@RequestMapping("/index")
	public String index() {
		return "/roof-web/web/menu/menu_index.jsp";
	}

	@RequestMapping(value = "base",method = {RequestMethod.GET})
	public @ResponseBody Result base() {
		Map<String,Object> map = Maps.newHashMap();
		List<TreeSelectDate> resourceVos = resourceService.recursionByParent(0L);
		map.put("resources",resourceVos);
		MenuType[] menuTypes = MenuType.values();
		map.put("menuTypes",menuTypes);

		return new Result(Result.SUCCESS, map);
	}

	@RequestMapping(value = "read",method = {RequestMethod.GET})
	public @ResponseBody Result read(Long parentId) {
		List<MenuVo> list =  menuService.read(parentId);
		return new Result(Result.SUCCESS,list);
	}

	@RequestMapping(value = "/{id}",method = {RequestMethod.GET})
	public @ResponseBody Result detail(@PathVariable Long id) {
		Menu menu = menuService.load(id);
		//model.addAttribute("menuTypes", MenuType.values());
		//model.addAttribute("menu", menu);
		return new Result(Result.SUCCESS,menu);
	}

	@RequestMapping(method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody MenuDto menuDto) {
		if (menuDto != null) {
			Menu menu = new Menu();
			BeanUtils.copyProperties(menuDto,menu);
			menu.setDtype(Menu.class.getSimpleName());
			menuService.create(menuDto.getParentId(), menu);
			return new Result("保存成功!");
		}
		return new Result("数据传输失败!");
	}



	@RequestMapping(value = "/{id}",method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id) {
		menuService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
	public @ResponseBody Result update(@PathVariable Long id,@RequestBody Menu menu) {
		Result result = new Result("数据传输失败!");
		if (menu != null) {
			menu.setId(id);
			if (menu.getModule() != null) {
				if (menu.getModule().getId() == null || menu.getModule().getId().longValue() == 0) {
					menu.setModule(null);
				}
			}
			menuService.update(menu);
			result = new Result("保存成功!");
		}
		return result;
	}

	@RequestMapping("/moveTo")
	public @ResponseBody Result moveTo(@RequestParam Long parentId, @RequestParam Long id) {
		Menu parent = menuService.load(parentId);
		Menu menu = menuService.load(id);
		menu.setParent(parent);
		if (menuService.childrenCount(parent.getId()) == 0) {
			parent.setLeaf(true);
		} else {
			parent.setLeaf(false);
		}
		menuService.update(parent);
		menuService.update(menu);
		return new Result("移动成功!");
	}

	@Autowired(required = true)
	public void setMenuService(@Qualifier("menuService") IMenuService menuService) {
		this.menuService = menuService;
	}
}
