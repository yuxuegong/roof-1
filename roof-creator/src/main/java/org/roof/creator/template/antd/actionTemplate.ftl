package ${packagePath}.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.entity.${alias?cap_first}Vo;
import ${packagePath}.service.api.I${alias?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

<#assign key = primaryKey[0] />
@Controller
@RequestMapping("${projectName}")
public class ${alias?cap_first}Controller {
	private I${alias?cap_first}Service ${alias}Service;




    @RequestMapping(value = "${actionName}", method = {RequestMethod.GET})
    public @ResponseBody Result<Page> list(${alias?cap_first} ${alias}, HttpServletRequest request, Model model) {
	    Page page = PageUtils.createPage(request);
	    page = ${alias}Service.page(page, ${alias});
	    return new Result(Result.SUCCESS, page);
	}
	


	@RequestMapping(value = "${actionName}", method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody ${alias?cap_first} ${alias}) {
		if (${alias} != null) {
			${alias}Service.save(${alias});
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}

    @RequestMapping(value = "${actionName}/{id}", method = {RequestMethod.GET})
    public @ResponseBody Result<${alias?cap_first}Vo> load(@PathVariable Long id) {
		${alias?cap_first}Vo ${alias}Vo = ${alias}Service.load(new ${alias?cap_first}(id));
        return new Result(Result.SUCCESS,${alias}Vo);
    }
	
	@RequestMapping(value = "${actionName}/{id}", method = {RequestMethod.PUT})
	public @ResponseBody Result update(@PathVariable Long id ,@RequestBody ${alias?cap_first} ${alias}) {
		if (id != null && ${alias} != null) {
			${alias}.setId(id);
			${alias}Service.updateIgnoreNull(${alias});
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}
	
	@RequestMapping(value = "${actionName}/{id}", method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id ) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		${alias}Service.delete(new ${alias?cap_first}(id));
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void set${alias?cap_first}Service(
			@Qualifier("${alias}Service") I${alias?cap_first}Service ${alias}Service) {
		this.${alias}Service = ${alias}Service;
	}


}
