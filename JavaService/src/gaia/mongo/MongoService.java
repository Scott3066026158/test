//LORD_USERMANAGERSERVER_JAVA_S1:LORD100%;
//****************************************************************************\
//*                                                                             *
//* MongoService.java -MongoService functions, types, and definitions           *
//*                                                                             *
//*               Version 1.00 âââââ?                                                                                                                                  *
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
	 * åå»ºMongoæ°æ®åºæå?
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

		// éè¿è¿æ¥è®¤è¯è·åMongoDBè¿æ¥
		m_mongoClient = new MongoClient(addrs, credentials);
		// MongoClient m_mongoClient = new MongoClient("192.168.88.101", 27017);

		m_db = m_mongoClient.getDatabase("lordhdctest");
	}

	/**
	 * åå»ºMongoæ°æ®åºæå?
	 * 
	 * @param host
	 *            æå¡å¨å°å?
	 * @param port
	 *            æå¡å¨ç«¯å£å·
	 * @param userName
	 *            ç¨æ·å?
	 * @param pwd
	 *            ç¨æ·å¯ç 
	 * @param databaseName
	 *            æ°æ®åºåç§?
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

		// éè¿è¿æ¥è®¤è¯è·åMongoDBè¿æ¥
		m_mongoClient = new MongoClient(addrs, credentials);
		m_db = m_mongoClient.getDatabase(databaseName);
	}

	/**
	 * æ ¹æ®æå®çæ¡ä»¶å é¤æ°æ?
	 * 
	 * @param collectionName
	 *            éååç§°
	 * @param query
	 *            å é¤æ¡ä»¶
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
	 * å é¤å¨é¨
	 * 
	 * @param collectionName
	 *            éååç§°
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
	 * è·åCollection
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
	 * è·åDB
	 * 
	 * @return DB
	 */
	public MongoDatabase GetDB()
	{
		return m_db;
	}

	/**
	 * åéåä¸­æå¥æ°æ®
	 * 
	 * @param collectionName
	 *            éååç§°
	 * @param item
	 *            æå¥çæ°æ?
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
	 * åéåä¸­æ¹éæå¥æ°æ®
	 * 
	 * @param collectionName
	 *            éååç§°
	 * @param documents
	 *            æå¥çæ°æ®åè¡?
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
	 * æ¥æ¾éåä¸­çæ°æ®
	 * 
	 * @param <T>
	 * @param collectionName
	 *            éååç§°
	 * @param type
	 *            ç±»å
	 * @param query
	 *            æ¥è¯¢æ¡ä»¶
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
	 * mongo åé¡µ
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
	 * æ¥æ¾éåä¸­çæ°æ®
	 * 
	 * @param <T>
	 * @param collectionName
	 *            éååç§°
	 * @param type
	 *            ç±»å
	 * @param query
	 *            æ¥è¯¢æ¡ä»¶
	 * @param sort
	 *            æåºæ¡ä»¶
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
	 * æ¥æ¾éåä¸­çæ°æ®
	 * 
	 * @param collectionName
	 *            éååç§°
	 * @param type
	 *            ç±»å
	 * @param query
	 *            æ¥è¯¢æ¡ä»¶
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
	 * æ´æ°éåä¸­çæ°æ®
	 * 
	 * @param collectionName
	 *            æå¥çæ°æ?
	 * @param item
	 *            æ´æ°æ°æ®
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