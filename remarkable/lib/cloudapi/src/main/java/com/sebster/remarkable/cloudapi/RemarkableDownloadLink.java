package com.sebster.remarkable.cloudapi;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

import lombok.Value;

@Value
public class RemarkableDownloadLink {

	UUID itemId;
	URL url;
	Instant expirationTime;

}
