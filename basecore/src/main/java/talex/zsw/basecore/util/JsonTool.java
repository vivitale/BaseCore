package talex.zsw.basecore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 项目名称: BaseProject
 * 作用: Json解析,拼装类
 * 作者: 赵小白 email:vvtale@gmail.com  
 * 日期: 2015-11-09 14:02 
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class JsonTool
{
	private static Gson getGson()
	{
		GsonBuilder gb = new GsonBuilder();
		gb.setLenient();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
		gb.registerTypeAdapter(Double.class, new JsonSerializer<Double>()
		{
			@Override public JsonElement serialize(Double originalValue, Type typeOf, JsonSerializationContext context)
			{
				BigDecimal bigValue = BigDecimal.valueOf(originalValue);
				if(originalValue == originalValue.longValue())
				{
					return new JsonPrimitive(originalValue.longValue());
				}
				return new JsonPrimitive(bigValue.toPlainString());
			}
		});
		gb.registerTypeAdapter(Long.class, new JsonSerializer<Long>()
		{
			@Override public JsonElement serialize(Long originalValue, Type typeOf, JsonSerializationContext context)
			{
				BigDecimal bigValue = BigDecimal.valueOf(originalValue);
				return new JsonPrimitive(bigValue.toPlainString());
			}
		});
		gb.registerTypeAdapter(Integer.class, new JsonSerializer<Integer>()
		{
			@Override public JsonElement serialize(Integer originalValue, Type typeOf, JsonSerializationContext context)
			{
				BigDecimal bigValue = BigDecimal.valueOf(originalValue);
				return new JsonPrimitive(bigValue.toPlainString());
			}
		});
		gb.registerTypeAdapter(Float.class, new JsonSerializer<Float>()
		{
			@Override public JsonElement serialize(Float originalValue, Type typeOf, JsonSerializationContext context)
			{
				BigDecimal bigValue = BigDecimal.valueOf(originalValue);
				if(originalValue == originalValue.longValue())
				{
					return new JsonPrimitive(originalValue.longValue());
				}
				return new JsonPrimitive(bigValue.toPlainString());
			}
		});
		gb.registerTypeAdapter(new TypeToken<TreeMap<String, Object>>()
		{}.getType(), new JsonDeserializer<TreeMap<String, Object>>()
		{
			@Override
			public TreeMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException
			{

				TreeMap<String, Object> treeMap = new TreeMap<>();
				JsonObject jsonObject = json.getAsJsonObject();
				Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
				for(Map.Entry<String, JsonElement> entry : entrySet)
				{
					treeMap.put(entry.getKey(), entry.getValue());
				}
				return treeMap;
			}
		});
		return gb.create();
	}

	/**
	 * 将json解析成指定泛型并返回
	 *
	 * @param string json数据
	 * @param <T>    指定泛型
	 */
	public static <T> T getObject(String string, Class<T> t)
	{
		return getGson().fromJson(string, t);
	}

	/**
	 * 将object解析成指定泛型并返回
	 *
	 * @param obj json数据的object
	 * @param <T> 指定泛型
	 */
	public static <T> T getObject(Object obj, Class<T> t)
	{
		Gson gson = getGson();
		String data = gson.toJson(obj);
		if(RegTool.isNullString(data))
		{
			data = "";
		}
		return gson.fromJson(data, t);
	}

	/**
	 * 将object解析成指定泛型并返回
	 * TypeToken token =  new TypeToken<ArrayList<BannerVo>>(){};
	 * val list = JsonTool.getObject(response.data, object : TypeToken<ArrayList<SearchOrderVo>>(){})
	 *
	 * @param obj   json数据的object
	 * @param token 解析类型token
	 */
	public static Object getObject(Object obj, TypeToken token)
	{
		Gson gson = getGson();
		String data = gson.toJson(obj);
		if(RegTool.isNullString(data))
		{
			data = "";
		}
		return gson.fromJson(data, token.getType());
	}

	/**
	 * 将object解析成指定泛型并返回
	 * TypeToken token =  new TypeToken<ArrayList<BannerVo>>(){};
	 * val list = JsonTool.getObject(response.data, object : TypeToken<ArrayList<SearchOrderVo>>(){})
	 *
	 * @param string json数据
	 * @param token  解析类型token
	 */
	public static Object getObject(String string, TypeToken token)
	{
		Gson gson = getGson();
		if(RegTool.isNullString(string))
		{
			string = "";
		}
		return gson.fromJson(string, token.getType());
	}

	/**
	 * 将指定类变成Json型数据返回
	 *
	 * @param obj 指定类型
	 * @param <T> 指定泛型
	 */
	public static <T> String getJsonString(T obj)
	{
		Gson gson = getGson();
		String data = gson.toJson(obj);
		if(RegTool.isNullString(data))
		{
			data = "";
		}
		return data;
	}

	/**
	 * 将json字符串解析成Map
	 *
	 * @param jsonStr json数据的object
	 */
	public static Map<String, String> getMapFromJson(String jsonStr)
	{
		if(RegTool.isNullString(jsonStr))
		{
			return null;
		}
		JSONObject jsonObject;
		try
		{
			jsonObject = new JSONObject(jsonStr);

			Iterator<String> keyIter = jsonObject.keys();
			String key;
			String value;
			Map<String, String> valueMap = new HashMap<>();
			while(keyIter.hasNext())
			{
				key = keyIter.next();
				value = jsonObject.get(key).toString();
				if(!RegTool.isNullString(value))
				{
					valueMap.put(key, value);
				}
			}
			return valueMap;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将object解析成Map
	 *
	 * @param obj 数据object
	 */
	public static Map<String, String> getMapFromObj(Object obj)
	{
		String jsonStr = getJsonString(obj);
		return getMapFromJson(jsonStr);
	}

	/**
	 * 将json 数组转换为Map 对象
	 *
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap(String jsonString)
	{
		JSONObject jsonObject;
		try
		{
			jsonObject = new JSONObject(jsonString);
			@SuppressWarnings("unchecked") Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while(keyIter.hasNext())
			{
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json 数组转换为Map 对象
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getMap(Object obj)
	{
		String jsonStr = getJsonString(obj);
		return getMap(jsonStr);
	}


	/**
	 * 把json 转换为ArrayList 形式
	 *
	 * @return
	 */
	public static List<Map<String, Object>> getList(String jsonString)
	{
		List<Map<String, Object>> list = null;
		try
		{
			JSONArray jsonArray = new JSONArray(jsonString);
			JSONObject jsonObject;
			list = new ArrayList<Map<String, Object>>();
			for(int i = 0; i < jsonArray.length(); i++)
			{
				jsonObject = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObject.toString()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 把json 转换为ArrayList 形式
	 *
	 * @return
	 */
	public static List<Map<String, Object>> getList(Object obj)
	{
		String jsonStr = getJsonString(obj);
		return getList(jsonStr);
	}

}
