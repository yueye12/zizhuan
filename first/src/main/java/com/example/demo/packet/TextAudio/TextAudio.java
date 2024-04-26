package com.example.demo.packet.TextAudio;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.OutputFormatEnum;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerResponse;
import com.example.demo.controller.yue.SpeechSynthesizerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Component
public class  TextAudio implements SpeechSynthesizerDemo {
    private static final Logger logger = LoggerFactory.getLogger(SpeechSynthesizerController.class);

    private static long startTime;
    private String appKey;
    private NlsClient client;
    private String url;

    public TextAudio() throws IOException {
        AccessToken accessToken = new AccessToken("LTAI5tLxDZzVYf1okTssx4nY", "n73YnfSJAR8IHAd3pNEaVkloiAKMn6");
        accessToken.apply();
        String token = accessToken.getToken();
        this.startTime = 234;
        long expireTime = accessToken.getExpireTime();
        this.appKey = "260nffDr2R2DfBt8";
        this.url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";
        if (url.isEmpty()) {
            client = new NlsClient(token);
        } else {
            client = new NlsClient("wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1", token);
        }
    }
    @Override
    public void process(String text) {

        SpeechSynthesizer synthesizer = null;
        try {

            synthesizer = new SpeechSynthesizer(client, getSynthesizerListener());
            synthesizer.setAppKey(appKey);
            synthesizer.setFormat(OutputFormatEnum.WAV);
            synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            synthesizer.setVoice("siyue");
            synthesizer.setPitchRate(100);
            synthesizer.setSpeechRate(100);
            synthesizer.setText(text);
            synthesizer.addCustomedParam("enable_subtitle", true);

            long start = System.currentTimeMillis();
            synthesizer.start();
            logger.info("tts start latency " + (System.currentTimeMillis() - start) + " ms");
            TextAudio.startTime = System.currentTimeMillis();

            synthesizer.waitForComplete();
            logger.info("tts stop latency " + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (synthesizer != null) {
                synthesizer.close();
            }
        }
    }

    @Override
    public byte[] getSynthesizedSpeech() {
        return new byte[0];
    }


    private SpeechSynthesizerListener getSynthesizerListener() {
        return new SpeechSynthesizerListener() {
            File f = new File("tts_test.wav");
            FileOutputStream fout;

            {
                try {
                    fout = new FileOutputStream(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private boolean firstRecvBinary = true;

            @Override
            public void onComplete(SpeechSynthesizerResponse response) {
                System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", output file :" + f.getAbsolutePath());
            }

            @Override
            public void onMessage(ByteBuffer message) {
                try {
                    if (firstRecvBinary) {
                        firstRecvBinary = false;
                        long now = System.currentTimeMillis();
                        logger.info("tts first latency : " + (now - TextAudio.startTime) + " ms");
                    }
                    byte[] bytesArray = new byte[message.remaining()];
                    message.get(bytesArray, 0, bytesArray.length);
                    fout.write(bytesArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(SpeechSynthesizerResponse response) {
                System.out.println(
                        "task_id: " + response.getTaskId() +
                                ", status: " + response.getStatus() +
                                ", status_text: " + response.getStatusText());
            }

            @Override
            public void onMetaInfo(SpeechSynthesizerResponse response) {
                System.out.println("MetaInfo event:{}" + response.getTaskId());
            }
        };
    }
    public void shutdown() {
        this.client.shutdown();
    }

}