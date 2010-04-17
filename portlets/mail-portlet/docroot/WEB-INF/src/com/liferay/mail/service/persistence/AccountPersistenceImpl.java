/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchAccountException;
import com.liferay.mail.model.Account;
import com.liferay.mail.model.impl.AccountImpl;
import com.liferay.mail.model.impl.AccountModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AccountPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountPersistence
 * @see       AccountUtil
 * @generated
 */
public class AccountPersistenceImpl extends BasePersistenceImpl<Account>
	implements AccountPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AccountImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_A = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_A = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Account account) {
		EntityCacheUtil.putResult(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountImpl.class, account.getPrimaryKey(), account);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_A,
			new Object[] { new Long(account.getUserId()), account.getAddress() },
			account);
	}

	public void cacheResult(List<Account> accounts) {
		for (Account account : accounts) {
			if (EntityCacheUtil.getResult(
						AccountModelImpl.ENTITY_CACHE_ENABLED,
						AccountImpl.class, account.getPrimaryKey(), this) == null) {
				cacheResult(account);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AccountImpl.class.getName());
		EntityCacheUtil.clearCache(AccountImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Account create(long accountId) {
		Account account = new AccountImpl();

		account.setNew(true);
		account.setPrimaryKey(accountId);

		return account;
	}

	public Account remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Account remove(long accountId)
		throws NoSuchAccountException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Account account = (Account)session.get(AccountImpl.class,
					new Long(accountId));

			if (account == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + accountId);
				}

				throw new NoSuchAccountException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					accountId);
			}

			return remove(account);
		}
		catch (NoSuchAccountException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Account remove(Account account) throws SystemException {
		for (ModelListener<Account> listener : listeners) {
			listener.onBeforeRemove(account);
		}

		account = removeImpl(account);

		for (ModelListener<Account> listener : listeners) {
			listener.onAfterRemove(account);
		}

		return account;
	}

	protected Account removeImpl(Account account) throws SystemException {
		account = toUnwrappedModel(account);

		Session session = null;

		try {
			session = openSession();

			if (account.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AccountImpl.class,
						account.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(account);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AccountModelImpl accountModelImpl = (AccountModelImpl)account;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_A,
			new Object[] {
				new Long(accountModelImpl.getOriginalUserId()),
				
			accountModelImpl.getOriginalAddress()
			});

		EntityCacheUtil.removeResult(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountImpl.class, account.getPrimaryKey());

		return account;
	}

	public Account updateImpl(com.liferay.mail.model.Account account,
		boolean merge) throws SystemException {
		account = toUnwrappedModel(account);

		boolean isNew = account.isNew();

		AccountModelImpl accountModelImpl = (AccountModelImpl)account;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, account, merge);

			account.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AccountModelImpl.ENTITY_CACHE_ENABLED,
			AccountImpl.class, account.getPrimaryKey(), account);

		if (!isNew &&
				((account.getUserId() != accountModelImpl.getOriginalUserId()) ||
				!Validator.equals(account.getAddress(),
					accountModelImpl.getOriginalAddress()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_A,
				new Object[] {
					new Long(accountModelImpl.getOriginalUserId()),
					
				accountModelImpl.getOriginalAddress()
				});
		}

		if (isNew ||
				((account.getUserId() != accountModelImpl.getOriginalUserId()) ||
				!Validator.equals(account.getAddress(),
					accountModelImpl.getOriginalAddress()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_A,
				new Object[] { new Long(account.getUserId()), account.getAddress() },
				account);
		}

		return account;
	}

	protected Account toUnwrappedModel(Account account) {
		if (account instanceof AccountImpl) {
			return account;
		}

		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setNew(account.isNew());
		accountImpl.setPrimaryKey(account.getPrimaryKey());

		accountImpl.setAccountId(account.getAccountId());
		accountImpl.setCompanyId(account.getCompanyId());
		accountImpl.setUserId(account.getUserId());
		accountImpl.setUserName(account.getUserName());
		accountImpl.setCreateDate(account.getCreateDate());
		accountImpl.setModifiedDate(account.getModifiedDate());
		accountImpl.setAddress(account.getAddress());
		accountImpl.setPersonalName(account.getPersonalName());
		accountImpl.setProtocol(account.getProtocol());
		accountImpl.setIncomingHostName(account.getIncomingHostName());
		accountImpl.setIncomingPort(account.getIncomingPort());
		accountImpl.setIncomingSecure(account.isIncomingSecure());
		accountImpl.setOutgoingHostName(account.getOutgoingHostName());
		accountImpl.setOutgoingPort(account.getOutgoingPort());
		accountImpl.setOutgoingSecure(account.isOutgoingSecure());
		accountImpl.setLogin(account.getLogin());
		accountImpl.setPassword(account.getPassword());
		accountImpl.setSavePassword(account.isSavePassword());
		accountImpl.setSignature(account.getSignature());
		accountImpl.setUseSignature(account.isUseSignature());
		accountImpl.setFolderPrefix(account.getFolderPrefix());
		accountImpl.setInboxFolderId(account.getInboxFolderId());
		accountImpl.setDraftFolderId(account.getDraftFolderId());
		accountImpl.setSentFolderId(account.getSentFolderId());
		accountImpl.setTrashFolderId(account.getTrashFolderId());
		accountImpl.setDefaultSender(account.isDefaultSender());

		return accountImpl;
	}

	public Account findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Account findByPrimaryKey(long accountId)
		throws NoSuchAccountException, SystemException {
		Account account = fetchByPrimaryKey(accountId);

		if (account == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + accountId);
			}

			throw new NoSuchAccountException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				accountId);
		}

		return account;
	}

	public Account fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Account fetchByPrimaryKey(long accountId) throws SystemException {
		Account account = (Account)EntityCacheUtil.getResult(AccountModelImpl.ENTITY_CACHE_ENABLED,
				AccountImpl.class, accountId, this);

		if (account == null) {
			Session session = null;

			try {
				session = openSession();

				account = (Account)session.get(AccountImpl.class,
						new Long(accountId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (account != null) {
					cacheResult(account);
				}

				closeSession(session);
			}
		}

		return account;
	}

	public List<Account> findByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<Account> list = (List<Account>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ACCOUNT_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(AccountModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Account>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Account> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<Account> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Account> list = (List<Account>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_ACCOUNT_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AccountModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<Account>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Account>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Account findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAccountException, SystemException {
		List<Account> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAccountException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Account findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAccountException, SystemException {
		int count = countByUserId(userId);

		List<Account> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchAccountException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Account[] findByUserId_PrevAndNext(long accountId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchAccountException, SystemException {
		Account account = findByPrimaryKey(accountId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ACCOUNT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AccountModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, account);

			Account[] array = new AccountImpl[3];

			array[0] = (Account)objArray[0];
			array[1] = (Account)objArray[1];
			array[2] = (Account)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Account findByU_A(long userId, String address)
		throws NoSuchAccountException, SystemException {
		Account account = fetchByU_A(userId, address);

		if (account == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", address=");
			msg.append(address);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchAccountException(msg.toString());
		}

		return account;
	}

	public Account fetchByU_A(long userId, String address)
		throws SystemException {
		return fetchByU_A(userId, address, true);
	}

	public Account fetchByU_A(long userId, String address,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), address };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_A,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ACCOUNT_WHERE);

				query.append(_FINDER_COLUMN_U_A_USERID_2);

				if (address == null) {
					query.append(_FINDER_COLUMN_U_A_ADDRESS_1);
				}
				else {
					if (address.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_A_ADDRESS_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_A_ADDRESS_2);
					}
				}

				query.append(AccountModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (address != null) {
					qPos.add(address);
				}

				List<Account> list = q.list();

				result = list;

				Account account = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_A,
						finderArgs, list);
				}
				else {
					account = list.get(0);

					cacheResult(account);

					if ((account.getUserId() != userId) ||
							(account.getAddress() == null) ||
							!account.getAddress().equals(address)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_A,
							finderArgs, account);
					}
				}

				return account;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_A,
						finderArgs, new ArrayList<Account>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Account)result;
			}
		}
	}

	public List<Account> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Account> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Account> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Account> list = (List<Account>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_ACCOUNT);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ACCOUNT.concat(AccountModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Account>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Account>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Account>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (Account account : findByUserId(userId)) {
			remove(account);
		}
	}

	public void removeByU_A(long userId, String address)
		throws NoSuchAccountException, SystemException {
		Account account = findByU_A(userId, address);

		remove(account);
	}

	public void removeAll() throws SystemException {
		for (Account account : findAll()) {
			remove(account);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ACCOUNT_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_A(long userId, String address)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), address };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ACCOUNT_WHERE);

				query.append(_FINDER_COLUMN_U_A_USERID_2);

				if (address == null) {
					query.append(_FINDER_COLUMN_U_A_ADDRESS_1);
				}
				else {
					if (address.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_A_ADDRESS_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_A_ADDRESS_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (address != null) {
					qPos.add(address);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ACCOUNT);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.mail.model.Account")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Account>> listenersList = new ArrayList<ModelListener<Account>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Account>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AttachmentPersistence.class)
	protected AttachmentPersistence attachmentPersistence;
	@BeanReference(type = FolderPersistence.class)
	protected FolderPersistence folderPersistence;
	@BeanReference(type = MessagePersistence.class)
	protected MessagePersistence messagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ACCOUNT = "SELECT account FROM Account account";
	private static final String _SQL_SELECT_ACCOUNT_WHERE = "SELECT account FROM Account account WHERE ";
	private static final String _SQL_COUNT_ACCOUNT = "SELECT COUNT(account) FROM Account account";
	private static final String _SQL_COUNT_ACCOUNT_WHERE = "SELECT COUNT(account) FROM Account account WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "account.userId = ?";
	private static final String _FINDER_COLUMN_U_A_USERID_2 = "account.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_A_ADDRESS_1 = "account.address IS NULL";
	private static final String _FINDER_COLUMN_U_A_ADDRESS_2 = "account.address = ?";
	private static final String _FINDER_COLUMN_U_A_ADDRESS_3 = "(account.address IS NULL OR account.address = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "account.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Account exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Account exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AccountPersistenceImpl.class);
}