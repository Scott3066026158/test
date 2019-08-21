package gaia.data;

import java.util.ArrayList;
import java.util.List;

//这个类结构定义了web端返回给前端的数据结构类型
public class ResultMapping <T>{
	public List<T> m_data;
	public String m_functionID;
}
