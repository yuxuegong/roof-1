package org.roof.web.dictionary.action;

import java.util.List;

import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryDto;
import org.roof.web.dictionary.entity.DictionaryVo;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("base/dictionary")
public class DictionaryController {

	private IDictionaryService dictionaryService;


	@RequestMapping(value = "/read",method = {RequestMethod.GET})
	public @ResponseBody Result read(String type) {
		List<DictionaryVo> vos = dictionaryService.read(type);
		return new Result(Result.SUCCESS,vos);
	}

	@RequestMapping(value = "/readVal",method = {RequestMethod.GET})
	public @ResponseBody Result  readVal(String type, String val) {
		List<DictionaryVo> vos = dictionaryService.readVal(type, val);
		return new Result(Result.SUCCESS,vos);

	}



	@RequestMapping( method = {RequestMethod.POST})
	public @ResponseBody Result create(@RequestBody DictionaryDto dictionaryDto) {
		if (dictionaryDto != null) {
			Dictionary dictionary = new Dictionary();
			BeanUtils.copyProperties(dictionaryDto,dictionary);
			dictionaryService.create(dictionaryDto.getParentId(), dictionary);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(@PathVariable Long id) {
		dictionaryService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public @ResponseBody Result<Dictionary> load(@PathVariable Long id) {
		Dictionary dictionary = dictionaryService.load(new Dictionary(id));
		return new Result(Result.SUCCESS,dictionary);
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
	public @ResponseBody Result update(@RequestBody Dictionary dictionary) {
		if (dictionary != null) {
			dictionaryService.updateIgnoreNull(dictionary);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/query")
	public @ResponseBody List<Dictionary> query(String type, String val, String text) {
		return dictionaryService.query(type, val, text, null);
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}
