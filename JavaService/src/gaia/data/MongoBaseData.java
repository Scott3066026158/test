package gaia.data;

import org.bson.Document;

import gaia.util.DocumentUtil;

public abstract class MongoBaseData
{
	public abstract Document ConvertObjectToDocument();

	public static <T extends MongoBaseData> T ConvertDocumentToObject(Document document, Class<T> type)
	{
		T t = null;
		try
		{
			t = DocumentUtil.Map2Object(type, document);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return t;
	}
}
