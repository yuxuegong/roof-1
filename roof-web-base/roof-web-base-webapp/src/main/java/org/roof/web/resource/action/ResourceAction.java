package org.roof.web.resource.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryVo;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.resource.entity.ResourceVo;
import org.roof.web.resource.service.api.IResourceService;
import org.roof.web.resource.service.api.IResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("base/resource")
public class ResourceAction {
	private static final Logger logger = LoggerFactory.getLogger(ResourceAction.class);
	private IResourceService resourceService;
	private IResourcesUtils resourcesUtils;


	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public @ResponseBody Result<Privilege> load(@PathVariable Long id) {
		Privilege resource = resourceService.load(id);
		if (resource.getParent() != null && resource.getParent().getId() != null) {
			resource.setParent(resourceService.load(resource.getParent().getId()));
		}
		return new Result(Result.SUCCESS,resource);
	}


	/**
	 * 创建一个新的资源
	 *
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody Privilege privilege) {
		try {
			Long parentId = privilege.getParent().getId();
			String type = privilege.getDtype();
			resourceService.create(parentId, type);
			return new Result(Result.SUCCESS, "新增成功!");
		}catch (Exception e){
			logger.error("新增资源",e);
			return new Result(Result.FAIL, "新增失败");
		}

	}

	@RequestMapping(value="/{id}",method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id) {
		resourceService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping("/updateModule")
	public @ResponseBody Result updateModule(Integer initBasic, Privilege module) {
		if (module != null && module.getId() != null) {
			resourceService.updateIgnoreNull(module);
			module = (Privilege) resourceService.load(module.getId());
			if (module.getLeaf() != null && initBasic != null && module.getLeaf() && initBasic == 1) {
				resourcesUtils.initBasicOperation(module);
				module.setLeaf(false);
				resourceService.updateIgnoreNull(module);
			}
		}
		return new Result("保存成功!");
	}

	@RequestMapping("/updatePrivilege")
	public @ResponseBody Result updatePrivilege(Privilege privilege) {
		if (privilege != null) {
			resourceService.updateIgnoreNull(privilege);
		}
		return new Result("保存成功!");
	}

	@RequestMapping(value = "/tree",method = {RequestMethod.GET})
	public @ResponseBody Result read(Long parentId) {
		List<Privilege> resources = resourceService.findPrivilegeByParent(parentId);
		return new Result(Result.SUCCESS,resources);
	}

	/**
	 * 根据父节点ID加载资源列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/read")
	public @ResponseBody List<ResourceVo> read(Long id, Long roleId, HttpServletRequest request, Model model) {
		String basePath = request.getContextPath();
		if (roleId == null || roleId.longValue() == 0) {
			return resourceService.read(id, basePath);
		} else {
			return resourceService.readByRole(id, roleId, basePath);
		}
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public IResourcesUtils getResourcesUtils() {
		return resourcesUtils;
	}

	@Autowired(required = true)
	public void setResourceService(@Qualifier("resourceService") IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired(required = true)
	public void setResourcesUtils(@Qualifier("resourcesUtils") IResourcesUtils resourcesUtils) {
		this.resourcesUtils = resourcesUtils;
	}

}
