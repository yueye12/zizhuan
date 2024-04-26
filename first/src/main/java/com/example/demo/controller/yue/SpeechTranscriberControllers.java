package com.example.demo.controller.yue;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import com.example.demo.packet.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(
        origins = {"*"}
)

public class SpeechTranscriberControllers {
    public SpeechTranscriberControllers() {
    }

    @PostMapping({"/transcribeAudio"})
    public JsonResult transcribeAudio(@RequestBody byte[] audioBytes) {
        if (audioBytes != null && audioBytes.length != 0) {
            try {
                String audioFilePath = this.saveAudioFile(audioBytes);
                SpeechTranscriberDemo demo = new SpeechTranscriberDemo();
                String transcription = String.valueOf(demo.process(audioFilePath));
                return new JsonResult(transcription,"200","成功");

            } catch (IOException var5) {
                return new JsonResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing audio file."),"100","错误");
            }
        } else {
            return new JsonResult(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Audio data is empty."),"200","错误");
        }
    }

    private String saveAudioFile(byte[] audioBytes) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        String audioFilePath = tempDir + "/temp_audio.wav";
        FileOutputStream fos = new FileOutputStream(audioFilePath);

        try {
            fos.write(audioBytes);
        } catch (Throwable var8) {
            try {
                fos.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        fos.close();
        return audioFilePath;
    }

    public static class SpeechTranscriberDemo {
        private String appKey;
        private NlsClient client;
        private String url;
        private static final Logger logger = LoggerFactory.getLogger(SpeechTranscriberDemo.class);
        private static List<String> transcriptionResults = new ArrayList();
        private String ee;
        private String res;

        public SpeechTranscriberDemo() throws IOException {


            AccessToken accessToken = new AccessToken("LTAI5tLxDZzVYf1okTssx4nY", "n73YnfSJAR8IHAd3pNEaVkloiAKMn6");
            accessToken.apply();
            String token = accessToken.getToken();
            long expireTime = accessToken.getExpireTime();
            this.appKey = "SQabH5tusPiXEC4y";
            this.url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";
            if (url.isEmpty()) {
                client = new NlsClient(token);
            } else {
                client = new NlsClient(url, token);
            }
        }
        public static SpeechTranscriberListener getTranscriberListener() {
            SpeechTranscriberListener listener = new SpeechTranscriberListener() {
                public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", name: " + response.getName() + ", status: " + response.getStatus() + ", index: " + response.getTransSentenceIndex() + ", result: " + response.getTransSentenceText() + ", time: " + response.getTransSentenceTime());
                }

                public void onTranscriberStart(SpeechTranscriberResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", name: " + response.getName() + ", status: " + response.getStatus());
                }

                public void onSentenceBegin(SpeechTranscriberResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", name: " + response.getName() + ", status: " + response.getStatus());
                }

                public void onSentenceEnd(SpeechTranscriberResponse response) {
                    String var10000 = response.getTaskId();
                    String transcriptionResult = "task_id: " + var10000 + ", name: " + response.getName() + ", status: " + response.getStatus() + ", index: " + response.getTransSentenceIndex() + ", result: " + response.getTransSentenceText() + ", confidence: " + response.getConfidence() + ", begin_time: " + response.getSentenceBeginTime() + ", time: " + response.getTransSentenceTime();
                    SpeechTranscriberControllers  .SpeechTranscriberDemo.transcriptionResults.add(0, response.getTransSentenceText());
                    System.out.println(transcriptionResult);
                }

                public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", name: " + response.getName() + ", status: " + response.getStatus());
                }

                public void onFail(SpeechTranscriberResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", status: " + response.getStatus() + ", status_text: " + response.getStatusText());
                }
            };
            return listener;
        }

        public static int getSleepDelta(int dataSize, int sampleRate) {
            // 仅支持16位采样
            int sampleBytes = 16;
            // 仅支持单通道
            int soundChannel = 1;
            return dataSize * 10 * 8000 / (160 * sampleRate);
        }
        public Object process(String filepath) {
            SpeechTranscriber transcriber = null;
            try {
                //创建实例,建立连接
                transcriber = new SpeechTranscriber(client, getTranscriberListener());
                transcriber.setAppKey(appKey);
                //输入音频编码方式
                transcriber.setFormat(InputFormatEnum.PCM);
                //输入音频采样率
                transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_8K);
                //是否返回中间识别结果
                transcriber.setEnableIntermediateResult(false);
                //是否生成并返回标点符号
                transcriber.setEnablePunctuation(true);
                //是否将返回结果规整化,比如将一百返回为100
                transcriber.setEnableITN(false);

                //此方法将以上参数设置序列化为json发送给服务端,并等待服务端确认
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
                    int deltaSleep = getSleepDelta(len, 8000);
                    Thread.sleep(deltaSleep);
                }

                //通知服务端语音数据发送完毕,等待服务端处理完成
                long now = System.currentTimeMillis();
                logger.info("ASR wait for complete");
                transcriber.stop();
                logger.info("ASR latency : " + (System.currentTimeMillis() - now) + " ms");
                List var9=null;
                var9 = Collections.singletonList((String) transcriptionResults.get(0));
                System.out.println("输出var9:"+var9);
                return var9;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                if (null != transcriber) {
                    transcriber.close();
                }
            }
            return null;
        }

//        public List<String> process(String filepath) {
//            SpeechTranscriber transcriber = null;
//
//            List var4;
//            try {
//                transcriber = new SpeechTranscriber(this.client, getTranscriberListener());
//                transcriber.setAppKey(this.appKey);
//                transcriber.setFormat(InputFormatEnum.PCM);
//                transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
//                transcriber.setEnableIntermediateResult(false);
//                transcriber.setEnablePunctuation(true);
//                transcriber.setEnableITN(false);
//                transcriber.start();
//                File file = new File(filepath);
//                FileInputStream fis = new FileInputStream(file);
//                byte[] b = new byte[3200];
//
//                int len;
//                while((len = fis.read(b)) > 0) {
//                    logger.info("send data pack length: " + len);
//                    transcriber.send(b, len);
//                    int deltaSleep = getSleepDelta(len, 16000);
//                    Thread.sleep((long)deltaSleep);
//                }
//
//                long now = System.currentTimeMillis();
//                logger.info("ASR wait for complete");
//                this.res = transcriber.getFormat();
//                transcriber.stop();
//                logger.info("ASR latency : " + (System.currentTimeMillis() - now) + " ms");
//                List var9=null;
//                var9 = Collections.singletonList((String) transcriptionResults.get(0));
//                System.out.println("输出var9:"+var9);
//                return var9;
//            } catch (Exception var13) {
//                System.err.println(var13.getMessage());
//                this.ee = var13.getMessage();
//                var4 = Collections.singletonList(this.ee);
//
//            } finally {
//                if (null != transcriber) {
//                    transcriber.close();
//                }
//
//            }
//            System.out.println("输出var4:");
//            return var4;
//
//        }

        public void shutdown() {
            this.client.shutdown();
        }
    }
}

