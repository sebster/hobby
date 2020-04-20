package com.sebster.remarkable.cloudapi;

public interface RemarkableClientFactory {

	RemarkableClientInfo register(String code);

	RemarkableClient createClient(RemarkableClientInfo clientInfo);

}
