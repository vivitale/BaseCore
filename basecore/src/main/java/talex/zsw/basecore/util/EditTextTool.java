package talex.zsw.basecore.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 作用: 输入框控制
 * 作者: 赵小白 email:edisonzsw@icloud.com
 * 日期: 2017/11/29 14:28
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class EditTextTool
{
	/**
	 * 禁止EditText输入特殊字符
	 */
	public static void setInhibitInputSpeChat(EditText editText)
	{
		InputFilter filter = new InputFilter()
		{
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
			{
				String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
				Pattern pattern = Pattern.compile(speChat);
				Matcher matcher = pattern.matcher(source.toString());
				if(matcher.find())
				{
					return "";
				}
				else
				{
					return null;
				}
			}
		};
		editText.setFilters(new InputFilter[]{filter});
	}

	/**
	 * 设置editText是否显示密码
	 */
	public static void setEditShowPass(EditText editText, boolean isShow)
	{
		if(isShow)
		{
			// 显示密码
			editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		else
		{
			// 隐藏密码
			editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

	/**
	 * 使editText不可编辑
	 */
	public static void lockEdit(EditText editText, boolean value)
	{
		editText.setFocusable(false);
		editText.setFocusableInTouchMode(false);
		if(value)
		{
			editText.setFilters(new InputFilter[]{new InputFilter()
			{
				@Override
				public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
				{
					return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
				}
			}});
		}
		else
		{
			editText.setFilters(new InputFilter[]{new InputFilter()
			{
				@Override
				public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
				{
					return null;
				}
			}});
		}
	}

	/**
	 * 限制输入的表达式
	 */
	public static String stringFilter(String str) throws PatternSyntaxException
	{
		String regEx = "[^a-zA-Z\u4E00-\u9FA5]";// 只允许字母和汉字
		//		String regEx = "[^0-9\u4E00-\u9FA5]";// 只允许数字和汉子
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void limitInput(final EditText editText)
	{
		editText.addTextChangedListener(new TextWatcher()
		{
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				String editable = editText.getText().toString();
				String str = stringFilter(editable.toString());
				if(!editable.equals(str))
				{
					editText.setText(str);
					//设置新的光标所在位置
					editText.setSelection(str.length());
				}
			}

			@Override public void afterTextChanged(Editable s)
			{

			}
		});
	}

	/**
	 * Edittext 首位小数点自动加零，最多两位小数
	 *
	 * @param editText EditText
	 */
	public static void setEdTwoDecimal(EditText editText)
	{
		setEdDecimal(editText, 2);
	}

	/**
	 * 设置最多几位小数
	 *
	 * @param editText EditText
	 * @param count 小数位数
	 */
	public static void setEdDecimal(EditText editText, int count)
	{
		if(count < 0)
		{
			count = 0;
		}

		count += 1;

		editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

		//设置字符过滤
		final int finalCount = count;
		editText.setFilters(new InputFilter[]{new InputFilter()
		{
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
			{
				if(".".contentEquals(source) && dest.toString().length() == 0)
				{
					return "0.";
				}
				if(dest.toString().contains("."))
				{
					int index = dest.toString().indexOf(".");
					int mlength = dest.toString().substring(index).length();
					if(mlength == finalCount)
					{
						return "";
					}
				}

				if(dest.toString().equals("0") && source.equals("0"))
				{
					return "";
				}

				return null;
			}
		}});
	}

	/**
	 * @param editText       输入框控件
	 * @param number         位数
	 *                       1 -> 1
	 *                       2 -> 01
	 *                       3 -> 001
	 *                       4 -> 0001
	 * @param isStartForZero 是否从000开始
	 *                       true -> 从 000 开始
	 *                       false -> 从 001 开始
	 */
	public static void setEditNumberAuto(final EditText editText, final int number, final boolean isStartForZero)
	{
		editText.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{
			@Override public void onFocusChange(View v, boolean hasFocus)
			{
				if(!hasFocus)
				{
					setEditNumber(editText, number, isStartForZero);
				}
			}
		});
	}

	/**
	 * @param editText       输入框控件
	 * @param number         位数
	 *                       1 -> 1
	 *                       2 -> 01
	 *                       3 -> 001
	 *                       4 -> 0001
	 * @param isStartForZero 是否从000开始
	 *                       true -> 从 000 开始
	 *                       false -> 从 001 开始
	 */
	public static void setEditNumber(EditText editText, int number, boolean isStartForZero)
	{
		StringBuilder s = new StringBuilder(editText.getText().toString());
		StringBuilder temp = new StringBuilder();

		int i;
		for(i = s.length(); i < number; ++i)
		{
			s.insert(0, "0");
		}
		if(!isStartForZero)
		{
			for(i = 0; i < number; ++i)
			{
				temp.append("0");
			}

			if(s.toString().equals(temp.toString()))
			{
				s = new StringBuilder(temp.substring(1)+"1");
			}
		}
		editText.setText(s.toString());
	}

	@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
	public void showSoftInputFromWindow(EditText editText){
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		editText.setSelection(editText.getText().length());
		InputMethodManager inputManager =
			(InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(editText, 0);
	}
}
