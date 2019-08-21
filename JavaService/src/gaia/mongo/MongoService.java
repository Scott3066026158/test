//LORD_USERMANAGERSERVER_JAVA_S1:LORD100%;
//****************************************************************************\
//*                                                                             *
//* MongoService.java -MongoService functions, types, and definitions           *
//*                                                                             *
//*               Version 1.00 ★★★★�?                                                                                                                                  *
//*                                                                             *
//*               Copyright (c) 2016-2016, Client. All rights reserved.         *
//*               Created by Lord.                                              *
//*                                                                             *
//*******************************************************************************

package gaia.mongo;

import java.util.List;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import gaia.data.MongoBaseData;

import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoService
{
	private MongoClient m_mongoClient = null;
	private MongoDatabase m_db = null;

	/**
	 * 创建Mongo数据库服�?
	 */
	public MongoService()
	{
		ServerAddress serverAddress = new ServerAddress("192.168.88.101", 27017);
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		com.mongodb.MongoCredential credential = MongoCredential.createScramSha1Credential("lordhdc", "lordhdctest",
				"gaia123".toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);

		// 通过连接认证获取MongoDB连接
		m_mongoClient = new MongoClient(addrs, credentials);
		// MongoClient m_mongoClient = new MongoClient("192.168.88.101", 27017);

		m_db = m_mongoClient.getDatabase("lordhdctest");
	}

	/**
	 * 创建Mongo数据库服�?
	 * 
	 * @param host
	 *            服务器地�?
	 * @param port
	 *            服务器端口号
	 * @param userName
	 *            用户�?
	 * @param pwd
	 *            用户密码
	 * @param databaseName
	 *            数据库名�?
	 */
	public MongoService(String host, int port, String userName, String pwd, String databaseName)
	{
		ServerAddress serverAddress = new ServerAddress(host, port);
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);

		com.mongodb.MongoCredential credential = MongoCredential.createScramSha1Credential(userName, databaseName,
				pwd.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);

		// 通过连接认证获取MongoDB连接
		m_mongoClient = new MongoClient(addrs, credentials);
		m_db = m_mongoClient.getDatabase(databaseName);
	}

	/**
	 * 根据指定的条件删除数�?
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param query
	 *            删除条件
	 */
	public void Delete(String collectionName, Bson query)
	{
		if (collectionName.isEmpty())
		{
			return;
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		DeleteResult result = collection.deleteMany(query);
	}

	/**
	 * 删除全部
	 * 
	 * @param collectionName
	 *            集合名称
	 */
	public void DeleteAll(String collectionName)
	{
		if (collectionName.isEmpty())
		{
			return;
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		DeleteResult result = collection.deleteMany(null);
	}

	/**
	 * 获取Collection
	 * 
	 * @return DBCollection
	 */
	public MongoCollection<Document> GetCollection(String collectionName)
	{
		if (null == m_db)
		{
			m_db = GetDB();
		}

		if (null != m_db)
		{
			return m_db.getCollection(collectionName);
		}

		return null;
	}

	/**
	 * 获取DB
	 * 
	 * @return DB
	 */
	public MongoDatabase GetDB()
	{
		return m_db;
	}

	/**
	 * 向集合中插入数据
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param item
	 *            插入的数�?
	 */
	public void Insert(String collectionName, MongoBaseData item)
	{
		if (collectionName.isEmpty())
		{
			return;
		}
		Document document = item.ConvertObjectToDocument();
		MongoCollection<Document> collection = GetCollection(collectionName);
		collection.insertOne(document);
	}

	/**
	 * 向集合中批量插入数据
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param documents
	 *            插入的数据列�?
	 */
	public <T extends MongoBaseData> void InsertBatch(String collectionName, List<T> items)
	{
		if (collectionName.isEmpty())
		{
			return;
		}
		List<Document> documents = new ArrayList<Document>();
		for (MongoBaseData data : items)
		{
			Document document = data.ConvertObjectToDocument();
			documents.add(document);
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		collection.insertMany(documents);
	}

	/**
	 * 查找集合中的数据
	 * 
	 * @param <T>
	 * @param collectionName
	 *            集合名称
	 * @param type
	 *            类型
	 * @param query
	 *            查询条件
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T extends MongoBaseData> List<T> QueryForList(String collectionName, Class<T> type, Bson query)
	{
		List<T> tList = new ArrayList<T>();
		if (collectionName.isEmpty())
		{
			return null;
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		FindIterable<Document> findIterable = null;
		if (query != null)
		{
			findIterable = collection.find(query);
		}
		else
		{
			findIterable = collection.find();
		}
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext())
		{
			Document document = mongoCursor.next();
			T t = T.ConvertDocumentToObject(document, type);
			tList.add(t);
		}
		return tList;
	}

	/**
	 * mongo 分页
	 * 
	 * @param collectionName
	 * @param type
	 * @param query
	 * @param pageNO
	 * @param pageSize
	 * @return
	 */
	public <T extends MongoBaseData> List<T> QueryForList(String collectionName, Class<T> type, Bson query, int pageNO,
			int pageSize)
	{
		List<T> tList = new ArrayList<T>();
		if (collectionName.isEmpty())
		{
			return null;
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		FindIterable<Document> findIterable = null;
		if (query != null)
		{
			findIterable = collection.find(query);
		}
		else
		{
			findIterable = collection.find();
		}
		findIterable.skip((pageNO - 1) * pageSize);
		findIterable.limit(pageSize);
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext())
		{
			Document document = mongoCursor.next();
			T t = T.ConvertDocumentToObject(document, type);
			tList.add(t);
		}
		return tList;
	}

	/**
	 * 查找集合中的数据
	 * 
	 * @param <T>
	 * @param collectionName
	 *            集合名称
	 * @param type
	 *            类型
	 * @param query
	 *            查询条件
	 * @param sort
	 *            排序条件
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T extends MongoBaseData> List<T> QueryForList(String collectionName, Class<T> type, Bson query, Bson sort)
	{
		if (collectionName.isEmpty())
		{
			return null;
		}
		List<T> tList = new ArrayList<T>();
		MongoCollection<Document> collection = GetCollection(collectionName);
		FindIterable<Document> findIterable = null;
		if (query != null)
		{
			findIterable = collection.find(query);
		}
		else
		{
			findIterable = collection.find();
		}
		if (sort != null)
		{
			findIterable = findIterable.sort(sort);
		}
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext())
		{
			Document document = mongoCursor.next();
			T t = T.ConvertDocumentToObject(document, type);
			tList.add(t);
		}
		return tList;
	}

	/**
	 * 查找集合中的数据
	 * 
	 * @param collectionName
	 *            集合名称
	 * @param type
	 *            类型
	 * @param query
	 *            查询条件
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T extends MongoBaseData> T FindOneAs(String collectionName, Class<T> type, Bson query)
	{
		if (collectionName.isEmpty())
		{
			return null;
		}
		MongoCollection<Document> collection = GetCollection(collectionName);
		FindIterable<Document> findIterable = collection.find(query);
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		if (mongoCursor.hasNext())
		{
			Document document = mongoCursor.next();
			T t = T.ConvertDocumentToObject(document, type);
			return t;
		}
		return null;
	}

	/**
	 * 更新集合中的数据
	 * 
	 * @param collectionName
	 *            插入的数�?
	 * @param item
	 *            更新数据
	 */
	public int Update(String collectionName, MongoBaseData item, Bson query)
	{
		if (collectionName.isEmpty())
		{
			return 0;
		}
		Document document = item.ConvertObjectToDocument();
		MongoCollection<Document> collection = GetCollection(collectionName);
		UpdateResult result = collection.replaceOne(query, document);
		long ret = result.getModifiedCount();
		return (int) ret;
	}
}