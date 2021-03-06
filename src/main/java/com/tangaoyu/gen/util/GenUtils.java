/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tangaoyu.gen.util;

import com.tangaoyu.gen.common.generator.ITypeConvert;
import com.tangaoyu.gen.common.generator.MySqlTypeConvert;
import com.tangaoyu.gen.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 代码生成工具类
 * @author ThinkGem
 * @version 2013-11-16
 */
public class GenUtils {

	private static Logger logger = LoggerFactory.getLogger(GenUtils.class);

	public static final String SUPERD_MAPPER_CLASS = "com.sendinfo.framework.boot.mapper.IBaseMapper";
	public static final String SUPERD_SERVICE_CLASS = "com.sendinfo.framework.boot.service.BaseService";
	public static final String SUPERD_SERVICEIMPL_CLASS = "com.sendinfo.framework.boot.service.impl.BaseServiceImpl";

	private static GenConfig genConfig;
	private static ITypeConvert iTypeConvert;

	static {
		genConfig = fileToObject("config.xml", GenConfig.class);
		iTypeConvert = new MySqlTypeConvert();
	}

	/**
	 * 初始化列属性字段
	 * @param genTable
	 */
	public static void initColumnField(Table genTable){
		for (TableColumn column : genTable.getColumnList()){
			
			// 如果是不是新增列，则跳过。
			if (StringUtils.isNotBlank(column.getId())){
				continue;
			}

			// 设置字段说明
			if (StringUtils.isBlank(column.getComments())){
				column.setComments(column.getName());
			}
			column.setJavaType(iTypeConvert.processTypeConvert(column.getJdbcType()).getPkg());
			column.setGenTable(genTable);

/*

			// 设置java类型
			if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "text")){
				column.setJavaType("String");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP")){
				column.setJavaType("java.util.Date");
				column.setShowType("dateselect");
			}else if(StringUtils.startsWithIgnoreCase(column.getJdbcType(), "decimal")){
				column.setJavaType("java.math.BigDecimal");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER")
					|| StringUtils.startsWithIgnoreCase(column.getJdbcType(), "int")
					){
				// 如果是浮点型
				String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
				if (ss != null && ss.length == 2 && Integer.parseInt(ss[1])>0){
					column.setJavaType("Double");
				}
				// 如果是整形
				else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0])<=11){  //这里为什么判断是11位，因为可能mysql把符号也算进来导致的11位
					column.setJavaType("Integer");
				}
				// 长整形
				else{
					column.setJavaType("Long");
				}
			}*/
			
			// 设置java字段名
			column.setJavaField(StringUtils.toCamelCase(column.getName()));


			genTable.getPkList().contains(column.getName());
			// 是否是主键
			column.setIsPk(genTable.getPkList().contains(column.getName())?"1":"0");

			// 插入字段
			if(!GenUtils.isBaseColumn(column.getSimpleJavaField())){
				column.setIsInsert("1");
			}

			// 编辑字段 注释 预留
/*			if (!StringUtils.equalsIgnoreCase(column.getName(), "id")
					&& !StringUtils.equalsIgnoreCase(column.getName(), "create_by")
					&& !StringUtils.equalsIgnoreCase(column.getName(), "create_date")
					&& !StringUtils.equalsIgnoreCase(column.getName(), "del_flag")){
				column.setIsEdit("1");
			}*/
			if(!GenUtils.isBaseColumn(column.getSimpleJavaField())){
				column.setIsEdit("1");
			}

			// 列表字段
			if (StringUtils.equalsIgnoreCase(column.getName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getName(), "title")
					|| StringUtils.equalsIgnoreCase(column.getName(), "remarks")
					|| StringUtils.equalsIgnoreCase(column.getName(), "update_date")){
				column.setIsList("1");
			}
			
			// 查询字段
			if (StringUtils.equalsIgnoreCase(column.getName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getName(), "title")){
				column.setIsQuery("1");
			}

			// 查询字段类型
			if (StringUtils.equalsIgnoreCase(column.getName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getName(), "title")){
				column.setQueryType("like");
			}

			// 查询字段类型
			if (StringUtils.isEmpty(column.getQueryType())){
				column.setQueryType("=");
			}

			// 设置特定类型和字段名
			
			// 用户
/*			if (StringUtils.startsWithIgnoreCase(column.getName(), "user_id")){
				column.setJavaType(User.class.getName());
				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setShowType("userselect");
			}
			// 部门
			else if (StringUtils.startsWithIgnoreCase(column.getName(), "office_id")){
				column.setJavaType(Office.class.getName());
				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setShowType("officeselect");
			}
			// 区域
			else if (StringUtils.startsWithIgnoreCase(column.getName(), "area_id")){
				column.setJavaType(Area.class.getName());
				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
				column.setShowType("areaselect");
			}
			// 创建者、更新者
			else if (StringUtils.startsWithIgnoreCase(column.getName(), "create_by")
					|| StringUtils.startsWithIgnoreCase(column.getName(), "update_by")){
				column.setJavaType(User.class.getName());
				column.setJavaField(column.getJavaField() + ".id");
			}*/
			// 创建时间、更新时间
			if (StringUtils.startsWithIgnoreCase(column.getJavaType(), "java.util.Date")){
				column.setShowType("dateselect");
			}
			// 备注、内容
			if (StringUtils.equalsIgnoreCase(column.getName(), "remarks")
					|| StringUtils.equalsIgnoreCase(column.getName(), "content")){
				column.setShowType("textarea");
			}
			// 父级ID
			if (StringUtils.equalsIgnoreCase(column.getName(), "parent_id")){
/*				column.setJavaType("This");
				column.setJavaField("parent.id|name");*/
				column.setShowType("treeselect");
			}
			// 所有父级ID
			if (StringUtils.equalsIgnoreCase(column.getName(), "parent_ids")){
				column.setQueryType("like");
			}
			// 删除标记
			if (StringUtils.equalsIgnoreCase(column.getName(), "is_delete")){
				column.setShowType("radiobox");
				column.setDictType("is_delete");
			}

			if(StringUtils.isBlank(column.getShowType())){
				column.setShowType("input");
			}

		}
	}
	
	/**
	 * 获取模板路径
	 * @return
	 */
	public static String getTemplatePath(){
		try{
			File file = new DefaultResourceLoader().getResource("").getFile();
			if(file != null){
				return file.getAbsolutePath() + File.separator + StringUtils.replaceEach(GenUtils.class.getName(), 
						new String[]{"util."+GenUtils.class.getSimpleName(), "."}, new String[]{"template", File.separator});
			}			
		}catch(Exception e){
			logger.error("{}", e);
		}

		return "";
	}

	/**
	 * 获取代码生成配置对象
	 * @return
	 */
	public static GenConfig getConfig(){
		return genConfig;
	}

	/**
	 * 获取java类型
	 * @return
	 */
	public static List<Dict> getJavaTypeList(){
		return genConfig.getJavaTypeList();
	}

	/**
	 * 获取查询类型
	 * @return
	 */
	public static List<Dict> getQueryTypeList(){
		return genConfig.getQueryTypeList();
	}

	/**
	 * 获取查询类型
	 * @return
	 */
	public static List<Dict> getShowTypeList(){
		return genConfig.getShowTypeList();
	}


	public static boolean isBaseColumn(String columnName){
		List<Dict> baseColumnList = genConfig.getBaseColumnList();
		if(CollectionUtils.isEmpty(baseColumnList))
			return false;
		return baseColumnList.stream().anyMatch(dict -> dict.getValue().equalsIgnoreCase(columnName));
	}

	/**
	 * XML文件转换为对象
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fileToObject(String fileName, Class<?> clazz){
		try {
			String pathName = "/templates/modules/gen/" + fileName;
//			logger.debug("File to object: {}", pathName);
			Resource resource = new ClassPathResource(pathName);
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null){
					break;
				}
				sb.append(line).append("\r\n");
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
//			logger.debug("Read file content: {}", sb.toString());
			return (T) JaxbMapper.fromXml(sb.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
		return null;
	}


	/**
	 * 根据分类获取模板列表
	 * @param category
	 * @param isChildTable 是否是子表
	 * @return
	 */
	public static List<GenTemplate> getTemplateList(String category, boolean isChildTable){
		GenConfig config = getConfig();
		if (CollectionUtils.isNotEmpty(config.getCategoryList())  && StringUtils.isNotBlank(category)){
			List<GenTemplate> genTemplates = config.getCategoryList().stream().filter(
					genCategory -> category.equals(genCategory.getValue()))
					.flatMap(genCategory -> {
						List<String> list = isChildTable ? genCategory.getChildTableTemplate() : genCategory.getTemplate();
						List<GenTemplate> templateList = new ArrayList<>();
						list.stream().forEach(s -> {
							if (StringUtils.startsWith(s, GenCategory.CATEGORY_REF)) {
								templateList.addAll(getTemplateList(StringUtils.replace(s, GenCategory.CATEGORY_REF, ""), false));
							} else {
								GenTemplate template = fileToObject(s, GenTemplate.class);
								if (template != null) {
									templateList.add(template);
								}
							}
						});
						return templateList.stream();
					}).collect(Collectors.toList());
			return genTemplates;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 获取数据模型
	 * @param genScheme
	 * @return
	 */
	public static Map<String, Object> getDataModel(GenScheme genScheme){
		Map<String, Object> model = new HashMap<>();

		// 是否lombok生成
		model.put("isLombok", true);
		model.put("packageName", StringUtils.lowerCase(genScheme.getPackageName()));
		model.put("lastPackageName", StringUtils.substringAfterLast((String)model.get("packageName"),"."));
		model.put("moduleName", StringUtils.lowerCase(genScheme.getModuleName()));
		model.put("subModuleName", StringUtils.lowerCase(genScheme.getSubModuleName()));
		model.put("bizModuleName", "biz");
		model.put("pkgBizModuleName", ".biz");
		model.put("className", StringUtils.uncapitalize(genScheme.getGenTable().getClassName()));
		model.put("ClassName", StringUtils.capitalize(genScheme.getGenTable().getClassName()));

		model.put("functionName", genScheme.getFunctionName());
		model.put("functionNameSimple", genScheme.getFunctionNameSimple());
		model.put("functionAuthor", genScheme.getFunctionAuthor());
		model.put("category",genScheme.getCategory());
		model.put("functionVersion", DateUtils.getDate());

		model.put("urlPrefix", model.get("moduleName")+(StringUtils.isNotBlank(genScheme.getSubModuleName())
				?"/"+StringUtils.lowerCase(genScheme.getSubModuleName()):"")+"/"+model.get("className"));
		model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
				model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(genScheme.getSubModuleName())
				?":"+StringUtils.lowerCase(genScheme.getSubModuleName()):"")+":"+model.get("className"));

		model.put("dbType", "mysql");

		model.put("table", genScheme.getGenTable());

		/*dao包*/
		model.put("superMapperClassPackage",GenUtils.SUPERD_MAPPER_CLASS);
		model.put("superMapperClass", StringUtils.substringAfterLast(GenUtils.SUPERD_MAPPER_CLASS,"."));

		/*service包*/
		model.put("superServiceClassPackage",GenUtils.SUPERD_SERVICE_CLASS);
		model.put("superServiceClass", StringUtils.substringAfterLast(GenUtils.SUPERD_SERVICE_CLASS,"."));

		/*service实现包*/
		model.put("superServiceImplClassPackage",GenUtils.SUPERD_SERVICEIMPL_CLASS);
		model.put("superServiceImplClass", StringUtils.substringAfterLast(GenUtils.SUPERD_SERVICEIMPL_CLASS,"."));


		model.put("baseResultMap",true);



		return model;
	}

	/**
	 * 生成到文件
	 * @param tpl
	 * @param model
	 * @param isReplaceFile
	 * @return
	 */
	public static String generateToFile(GenTemplate tpl, Map<String, Object> model, boolean isReplaceFile){

/*		model.put("currPkg",FreeMarkers.renderString(tpl.getFilePath(), model).replace("/","."));
		model.put("currClassName",StringUtils.substringBefore(FreeMarkers.renderString(tpl.getFileName(), model),"."));*/

		// 获取生成文件
		String fileName =FileUtils.getProjectPath() + File.separator
				+ StringUtils.replaceEach(FreeMarkers.renderString(tpl.getFilePath() + "/", model),
				new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator})
				+ FreeMarkers.renderString(tpl.getFileName(), model);
		logger.debug(" fileName === " + fileName);

		String content = "";
		// 获取生成文件内容
		if(StringUtils.isNotBlank(tpl.getContentPath())){
			content = FreeMarkers.renderTemplate(tpl.getContentPath(),model);
		}
		logger.debug(" content === \r\n" + content);
		// 如果选择替换文件，则删除原文件
		if (isReplaceFile){
			FileUtils.deleteFile(fileName);
		}

		// 创建并写入文件
		if (FileUtils.createFile(fileName)){
			FileUtils.writeToFile(fileName, content, true);
			logger.debug(" file create === " + fileName);
			return "生成成功："+fileName+"<br/>";
		}else{
			logger.debug(" file extents === " + fileName);
			return "文件已存在："+fileName+"<br/>";
		}
	}
}
