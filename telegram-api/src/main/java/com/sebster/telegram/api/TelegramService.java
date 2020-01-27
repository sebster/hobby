package com.sebster.telegram.api;

import java.time.Duration;
import java.util.List;

import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramFile;
import com.sebster.telegram.api.data.TelegramFileLink;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.messages.TelegramMessage;
import lombok.NonNull;

public interface TelegramService {

	/**
	 * Get the telegram user of the bot.
	 *
	 * @return the telegram user of the bot
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramUser getMe();

	/**
	 * Get up to 100 updates where the update id is greater than or equal to the specified offset. This call does not do long
	 * polling but returns immediately with an empty list if there are no updates.
	 *
	 * @param offset the minimum update id
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	List<TelegramUpdate> getUpdates(int offset);

	/**
	 * Get up to 100 updates (but no more than the specified limit) where the update id is greater than or equal to the specified
	 * offset. This call does not do long polling but returns immediately with an empty list if there are no updates.
	 *
	 * @param offset the minimum update id
	 * @param limit  limits the number of updates to be retrieved
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws IllegalArgumentException if the limit is less that 1 or greater than 100
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	List<TelegramUpdate> getUpdates(int offset, int limit);

	/**
	 * Get up to 100 updates where the update id is greater than or equal to the specified offset. Do long polling if timeout (in
	 * seconds) is greater than 0, respond immediately if the timeout is zero.
	 *
	 * @param offset  the minimum update id
	 * @param timeout the maximum number of seconds to wait for an update
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	List<TelegramUpdate> getUpdates(int offset, Duration timeout);

	/**
	 * Get up to 100 updates (but no more than the specified limit) where the update id is greater than or equal to the specified
	 * offset. Do long polling if the timeout duration is greater than 0 seconds, respond immediately if the timeout is zero.
	 *
	 * @param offset  the minimum update id
	 * @param limit   limits the number of updates to be retrieved
	 * @param timeout the maximum number of seconds to wait for an update
	 * @return the list of updates, or an empty list if there are no updates
	 * @throws IllegalArgumentException if the limit is less that 1 or greater than 100
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	List<TelegramUpdate> getUpdates(int offset, int limit, @NonNull Duration timeout);

	/**
	 * Use this method to get basic info about a file and prepare it for downloading. For the moment, bots can download files of up
	 * to 20MB in size. On success, a file link object is returned. The file can then be downloaded via the link
	 * https://api.telegram.org/file/bot&lt;token&gt;/&lt;file_path&gt;, where &lt;file_path&gt; is taken from the response. It is
	 * guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling
	 * getFileLink again.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramFileLink getFileLink(@NonNull String fileId);

	TelegramFileLink getFileLink(@NonNull TelegramFile file);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(long chatId, @NonNull String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(TelegramChat chat, @NonNull String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(String channel, @NonNull String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(long chatId, @NonNull TelegramSendMessageOptions options, @NonNull String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(TelegramChat chat, @NonNull TelegramSendMessageOptions options, @NonNull String text);

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.
	 *
	 * @throws TelegramServiceException if telegram returns an error response
	 */
	TelegramMessage sendMessage(String channel, @NonNull TelegramSendMessageOptions options, @NonNull String text);

}
