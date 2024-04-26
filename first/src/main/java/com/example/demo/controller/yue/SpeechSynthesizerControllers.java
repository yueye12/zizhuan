package com.example.demo.controller.yue;


import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.OutputFormatEnum;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class SpeechSynthesizerControllers {
    private static final Logger logger = LoggerFactory.getLogger(SpeechSynthesizerControllers.class);

    public SpeechSynthesizerControllers() {
    }

    @GetMapping({"/synthesizes"})
    public byte[] synthesizeSpeech(@RequestParam("text") String text, HttpServletResponse response) throws IOException {
        SpeechSynthesizerDemo demo = new SpeechSynthesizerDemo();
        demo.process(text);
        byte[] synthesizedSpeech = demo.getSynthesizedSpeech();
        demo.shutdown();
        response.reset();
        response.setContentType("audio/wav");
        response.setHeader("Content-Disposition", "attachment; filename=sample.wav");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(synthesizedSpeech);
        outputStream.flush();
        outputStream.close();
        System.out.println("Wav file downloaded successfully!");
        return synthesizedSpeech;
    }

    public static class SpeechSynthesizerDemo {
        private static long startTime;
        private String appKey;
        private NlsClient client;
        private String url;

        public SpeechSynthesizerDemo() throws IOException {
            AccessToken accessToken = new AccessToken("LTAI5tLxDZzVYf1okTssx4nY", "n73YnfSJAR8IHAd3pNEaVkloiAKMn6");
            accessToken.apply();
            String token = accessToken.getToken();
            long expireTime = accessToken.getExpireTime();
            this.appKey = "nzaGLxpNaWsvVCUO";
            this.url = "wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1";
            if (this.url.isEmpty()) {
                this.client = new NlsClient(token);
            } else {
                this.client = new NlsClient("wss://nls-gateway.cn-shanghai.aliyuncs.com/ws/v1", token);
            }

        }

        public void process(String text) {
            SpeechSynthesizer synthesizer = null;

            try {
                synthesizer = new SpeechSynthesizer(this.client, this.getSynthesizerListener());
                synthesizer.setAppKey(this.appKey);
                synthesizer.setFormat(OutputFormatEnum.WAV);
                synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
                synthesizer.setVoice("siyue");
                synthesizer.setPitchRate(100);
                synthesizer.setSpeechRate(100);
                synthesizer.setText(text);
                synthesizer.addCustomedParam("enable_subtitle", true);
                long start = System.currentTimeMillis();
                synthesizer.start();
                SpeechSynthesizerControllers.logger.info("tts start latency " + (System.currentTimeMillis() - start) + " ms");
                startTime = System.currentTimeMillis();
                synthesizer.waitForComplete();
                SpeechSynthesizerControllers.logger.info("tts stop latency " + (System.currentTimeMillis() - start) + " ms");
            } catch (Exception var8) {
                var8.printStackTrace();
            } finally {
                if (synthesizer != null) {
                    synthesizer.close();
                }

            }

        }

        public void shutdown() {
            this.client.shutdown();
        }

        private SpeechSynthesizerListener getSynthesizerListener() {
            return new SpeechSynthesizerListener() {
                File f = new File("tts_test.wav");
                FileOutputStream fout;
                private boolean firstRecvBinary;

                {
                    try {
                        this.fout = new FileOutputStream(this.f);
                    } catch (IOException var3) {
                        var3.printStackTrace();
                    }

                    this.firstRecvBinary = true;
                }

                public void onComplete(SpeechSynthesizerResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getName();
                    var10000.println("name: " + var10001 + ", status: " + response.getStatus() + ", output file :" + this.f.getAbsolutePath());
                }

                public void onMessage(ByteBuffer message) {
                    try {
                        if (this.firstRecvBinary) {
                            this.firstRecvBinary = false;
                            long now = System.currentTimeMillis();
                            SpeechSynthesizerControllers.logger.info("tts first latency : " + (now - SpeechSynthesizerControllers.SpeechSynthesizerDemo.startTime) + " ms");
                        }

                        byte[] bytesArray = new byte[message.remaining()];
                        message.get(bytesArray, 0, bytesArray.length);
                        this.fout.write(bytesArray);
                    } catch (IOException var4) {
                        var4.printStackTrace();
                    }

                }

                public void onFail(SpeechSynthesizerResponse response) {
                    PrintStream var10000 = System.out;
                    String var10001 = response.getTaskId();
                    var10000.println("task_id: " + var10001 + ", status: " + response.getStatus() + ", status_text: " + response.getStatusText());
                }

                public void onMetaInfo(SpeechSynthesizerResponse response) {
                    System.out.println("MetaInfo event:{}" + response.getTaskId());
                }
            };
        }

        public byte[] getSynthesizedSpeech() {
            try {
                Path filePath = Paths.get(ResourceUtils.getFile("tts_test.wav").getPath());
                return Files.readAllBytes(filePath);
            } catch (IOException var2) {
                var2.printStackTrace();
                return new byte[0];
            }
        }
    }

    public static class SynthesisRequest {
        private String appKey;
        private String token;
        private String url;
        private String text;

        public String getAppKey() {
            return this.appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public SynthesisRequest(String appKey, String token, String url, String text) {
            this.appKey = appKey;
            this.token = token;
            this.url = url;
            this.text = text;
        }

        public SynthesisRequest() {
        }

        public String toString() {
            return "SynthesisRequest{appKey='" + this.appKey + "', token='" + this.token + "', url='" + this.url + "', text='" + this.text + "'}";
        }
    }
}
