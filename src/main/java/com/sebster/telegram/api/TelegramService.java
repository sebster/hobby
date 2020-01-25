package com.sebster.telegram.api;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramFile;
import com.sebster.telegram.api.data.TelegramFileLink;
import com.sebster.telegram.api.data.TelegramUpdate;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramMessage;

public interface TelegramService {

	/**
	 * Get the telegram user of the bot.
	 *
	 * @return the telegram user of the bot
	 * @throws TelegramException
	 */
	TelegramUser getMe();

	/**
	 * Get up to 100 updates where the update id is greater than or equal to the
	 * specified offset. This call does not do long polling but returns
	 * immediately with an empty list if there are no updates.
	 *
	 * @param offset
	 *            the minimum update id
	 *
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramException
	 */
	List<TelegramUpdate> getUpdates(int offset);

	/**
	 * Get up to 100 updates (but no more than the specified limit) where the
	 * update id is greater than or equal to the specified offset. This call
	 * does not do long polling but returns immediately with an empty list if
	 * there are no updates.
	 *
	 * @param offset
	 *            the minimum update id
	 * @param limit
	 *            limits the number of updates to be retrieved
	 *
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramException
	 * @throws IllegalArgumentException
	 *             if the limit is less that 1 or greater than 100
	 */
	List<TelegramUpdate> getUpdates(int offset, int limit);

	/**
	 * Get up to 100 updates where the update id is greater than or equal to the
	 * specified offset. Do long polling if timeout (in seconds) is greater than
	 * 0, respond immediately if the timeout is zero.
	 *
	 * @param offset
	 *            the minimum update id
	 * @param timeout
	 *            the maximum number of seconds to wait for an update
	 *
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramException
	 */
	List<TelegramUpdate> getUpdates(int offset, Duration timeout);

	/**
	 * Get up to 100 updates (but no more than the specified limit) where the
	 * update id is greater than or equal to the specified offset. Do long
	 * polling if the timeout duration is greater than 0 seconds, respond
	 * immediately if the timeout is zero.
	 *
	 * @param offset
	 *            the minimum update id
	 * @param limit
	 *            limits the number of updates to be retrieved
	 * @param timeout
	 *            the maximum number of seconds to wait for an update
	 *
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramException
	 * @throws IllegalArgumentException
	 *             if the limit is less that 1 or greater than 100
	 */
	List<TelegramUpdate> getUpdates(int offset, int limit, Duration timeout);

	/**
	 * Use this method to get basic info about a file and prepare it for
	 * downloading. For the moment, bots can download files of up to 20MB in
	 * size. On success, a file link object is returned. The file can then be
	 * downloaded via the link https://api.telegram.org/file/bot<token>/
	 * <file_path>, where <file_path> is taken from the response. It is
	 * guaranteed that the link will be valid for at least 1 hour. When the link
	 * expires, a new one can be requested by calling getFileLink again.
	 * 
	 * @throws IOException
	 */
	TelegramFileLink getFileLink(String fileId);

	TelegramFileLink getFileLink(TelegramFile file);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(long chatId, String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(TelegramChat chat, String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(String channel, String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(long chatId, TelegramSendMessageOptions options, String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(TelegramChat chat, TelegramSendMessageOptions options, String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is
	 * returned.
	 */
	TelegramMessage sendMessage(String channel, TelegramSendMessageOptions options, String text);
}
