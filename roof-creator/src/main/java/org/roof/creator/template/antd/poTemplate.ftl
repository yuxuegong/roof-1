package ${packagePath}.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;

<#assign key = primaryKeyFields[0] />
/**
 * @author 模版生成 <br/>
 *         表名： ${tableName} <br/>
 *         描述：${tableDisplay} <br/>
 */
@ApiModel(value = "${tableName}", description = "${tableDisplay}")
public class ${alias?cap_first} implements Serializable {
	// 需要手动添加非默认的serialVersionUID
<#list fields as field>
	<#if field.dbType == "datetime">
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	<#elseif field.dbType == "date">
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	</#if>
	@ApiModelProperty(value = "${field.fieldDisplay}")
	protected ${field.fieldType} ${field.fieldName};// ${field.fieldDisplay}
</#list>

	public ${alias?cap_first}() {
		super();
	}

	public ${alias?cap_first}(Long ${key}) {
		super();
		this.${key} = ${key};
	}
	
<#list fields as field>
	<#if (field.fieldName == key)>@Id// 主键</#if>
	public ${field.fieldType} get${field.fieldName?cap_first}() {
		return ${field.fieldName};
	}
	public void set${field.fieldName?cap_first}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>
}
