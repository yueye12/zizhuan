package com.example.demo.packet.Audiotext;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class AudioText implements SpeechTranscriberDemo {
    private String appKey;
    private NlsClient client;
    private String url;



    private static final Logger logger = LoggerFactory.getLogger(SpeechTranscriberDemo.class);
    private static List<String> transcriptionResults = new ArrayList<>();
    private  String ee ;
    private  String res ;

    @Value("${ALIYUN_AK_ID}")
    private String akId;

    @Value("${ALIYUN_AK_SECRET}")
    private String akSecret;

    public AudioText() throws IOException {


        AccessToken accessToken = new AccessToken("LTAI5tLxDZzVYf1okTssx4nY", "n73YnfSJAR8IHAd3pNEaVkloiAKMn6");
        accessToken.apply();
        String token = accessToken.getToken();
        long expireTime = accessToken.getExpireTime();
        this.appKey = "lQKv2AeLUFsW6IrX";
        this.url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";
        if (url.isEmpty()) {
            client = new NlsClient(token);
        } else {
            client = new NlsClient(url, token);
        }
    }
    public static SpeechTranscriberListener getTranscriberListener() {
//            final List<String> transcriptionResults = new ArrayList<>();
        SpeechTranscriberListener listener = new SpeechTranscriberListener() {

            //TODO 识别出中间结果.服务端识别出一个字或词时会返回此消息.仅当setEnableIntermediateResult(true)时,才会有此类消息返回
            @Override
            public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                System.out.println("task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        //状态码 20000000 表示正常识别
                        ", status: " + response.getStatus() +
                        //句子编号，从1开始递增
                        ", index: " + response.getTransSentenceIndex() +
                        //当前的识别结果
                        ", result: " + response.getTransSentenceText() +
                        //当前已处理的音频时长，单位是毫秒
                        ", time: " + response.getTransSentenceTime());

            }

            @Override
            public void onTranscriberStart(SpeechTranscriberResponse response) {
                // TODO 重要提示： task_id很重要，是调用方和服务端通信的唯一ID标识，当遇到问题时，需要提供此task_id以便排查
                System.out.println("task_id: " + response.getTaskId() + ", name: " + response.getName() + ", status: " + response.getStatus());
            }

            @Override
            public void onSentenceBegin(SpeechTranscriberResponse response) {
                System.out.println("task_id: " + response.getTaskId() + ", name: " + response.getName() + ", status: " + response.getStatus());
            }

            @Override
            public void onSentenceEnd(SpeechTranscriberResponse response) {
                String transcriptionResult = "task_id: " + response.getTaskId() +
                        ", name: " + response.getName() +
                        ", status: " + response.getStatus() +
                        ", index: " + response.getTransSentenceIndex() +
                        ", result: " + response.getTransSentenceText() +
                        ", confidence: " + response.getConfidence() +
                        ", begin_time: " + response.getSentenceBeginTime() +
                        ", time: " + response.getTransSentenceTime();

                // 将转写结果添加到列表中
                transcriptionResults.add(0,response.getTransSentenceText());


                System.out.println(transcriptionResult);
            }
            //识别完毕
            @Override
            public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                System.out.println("task_id: " + response.getTaskId() + ", name: " + response.getName() + ", status: " + response.getStatus());
            }

            @Override
            public void onFail(SpeechTranscriberResponse response) {
                // TODO 重要提示： task_id很重要，是调用方和服务端通信的唯一ID标识，当遇到问题时，需要提供此task_id以便排查
                System.out.println("task_id: " + response.getTaskId() +  ", status: " + response.getStatus() + ", status_text: " + response.getStatusText());
            }
        };


        return listener;
    }

    /// 根据二进制数据大小计算对应的同等语音长度
    /// sampleRate 仅支持8000或16000
    public static int getSleepDelta(int dataSize, int sampleRate) {
        // 仅支持16位采样
        int sampleBytes = 16;
        // 仅支持单通道
        int soundChannel = 1;
        return (dataSize * 10 * 8000) / (160 * sampleRate);
    }

    public List<String> process(String filepath) {
        SpeechTranscriber transcriber = null;
        try {
            // 创建实例,建立连接
            transcriber = new SpeechTranscriber(client, getTranscriberListener());
            transcriber.setAppKey(appKey);
            // 输入音频编码方式
            transcriber.setFormat(InputFormatEnum.PCM);
            // 输入音频采样率
            transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            // 是否返回中间识别结果
            transcriber.setEnableIntermediateResult(false);
            // 是否生成并返回标点符号
            transcriber.setEnablePunctuation(true);
            // 是否将返回结果规整化,比如将一百返回为100
            transcriber.setEnableITN(false);

            // 此方法将以上参数设置序列化为json发送给服务端,并等待服务端确认
            transcriber.start();

            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[3200];
            int len;
            while ((len = fis.read(b)) > 0) {
                logger.info("send data pack length: " + len);
                transcriber.send(b, len);
                // TODO  重要提示：这里是用读取本地文件的形式模拟实时获取语音流并发送的，因为read很快，所以这里需要sleep
                // TODO  如果是真正的实时获取语音，则无需sleep, 如果是8k采样率语音，第二个参数改为8000
                // 8000采样率情况下，3200byte字节建议 sleep 200ms，16000采样率情况下，3200byte字节建议 sleep 100ms
                int deltaSleep = getSleepDelta(len, 16000);
                Thread.sleep(deltaSleep);
            }

//                 通知服务端语音数据发送完毕,等待服务端处理完成
            long now = System.currentTimeMillis();
            logger.info("ASR wait for complete");
            this.res =  transcriber.getFormat();
            transcriber.stop();
            logger.info("ASR latency : " + (System.currentTimeMillis() - now) + " ms");
            transcriber.getSpeechTranscriberListener();


            return Collections.singletonList(transcriptionResults.get(0));
            // 获取识别结果

        } catch (Exception e) {
            System.err.println(e.getMessage());
            this.ee= e.getMessage();
            return Collections.singletonList(this.ee);
        } finally {
            if (null != transcriber) {
                transcriber.close();
            }
        }
    }
    public void shutdown() {
        client.shutdown();
    }
}