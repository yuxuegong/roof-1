package org.roof.web.org.action;

import java.util.List;

import org.roof.spring.Result;
import org.roof.web.org.entity.OrgVo;
import org.roof.web.org.entity.Organization;
import org.roof.web.org.service.api.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("base/org")
public class OrgAction {

	private IOrgService orgService;


	@RequestMapping(value = "/read",method = {RequestMethod.GET})
	public @ResponseBody Result read(Long parentId) {
		List<OrgVo> orgVos = orgService.read(parentId);

		return new Result(Result.SUCCESS,orgVos);

	}


	@RequestMapping(value = "/{id}",method = {RequestMethod.GET})
	public @ResponseBody Result load(@PathVariable Long id) {
		Organization org =  orgService.load(id);
		return new Result(Result.SUCCESS,org);
	}


	@RequestMapping(method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody Organization org) {
		if (org != null) {
			orgService.create(org.getParent_id(), org);
			return new Result("保存成功!");
		}
		return new Result("数据传输失败!");
	}


	@RequestMapping(value = "/{id}",method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id) {
		orgService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping(value = "/{id}",method = {RequestMethod.PUT})
	public @ResponseBody Result update(@PathVariable Long id,@RequestBody Organization org) {
		if (org != null) {
			org.setId(id);
			orgService.updateIgnoreNull(org);
			return new Result("保存成功!");
		}
		return new Result("数据传输失败!");
	}

	@RequestMapping("/query")
	public @ResponseBody Object query(@ModelAttribute("org") Organization org) {
		if (org != null) {
			return orgService.selectForList(org);
		}
		return new Result("数据传输失败!");
	}

	@Autowired(required = true)
	public void setOrgService(@Qualifier("orgService") IOrgService orgService) {
		this.orgService = orgService;
	}

}
