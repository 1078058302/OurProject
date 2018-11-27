package com.bw.movie.net;

public interface HttpListener {
    void success(String data);

    void fail(String error);
}
