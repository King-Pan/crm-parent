package club.javalearn.crm.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-02-01
 **/
public class SshCommandExecutor {
    private Logger logger = LoggerFactory.getLogger(SshCommandExecutor.class);
    private String ipAddress;

    private String username;

    private String password;

    private static final int DEFAULT_SSH_PORT = 22;

    private Vector<String> stdout;

    public SshCommandExecutor(final String ipAddress, final String username, final String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        stdout = new Vector<>();
    }

    public int execute(final String command) {
        int returnCode = 0;
        JSch jsch = new JSch();
        SshUserInfo userInfo = new SshUserInfo();

        try {
            // Create and connect session.
            Session session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
            session.setPassword(password);
            session.setUserInfo(userInfo);
            session.connect();

            // Create and connect channel.
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader(channel
                    .getInputStream()));

            channel.connect();
            logger.info("The remote command is: " + command);

            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
            input.close();

            if (channel.isClosed()) {
                returnCode = channel.getExitStatus();
            }

            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            logger.error("执行脚本失败:" + command,e);
            e.printStackTrace();
            throw new RuntimeException("执行脚本失败:" + command);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("执行脚本失败:" + command,e);
            throw new RuntimeException("执行脚本失败:" + command);
        }
        return returnCode;
    }

    private Vector<String> getStandardOutput() {
        return stdout;
    }

    public static void main(final String [] args) {
        SshCommandExecutor sshExecutor = new SshCommandExecutor("192.168.1.200", "yh", "yh");
        String cmd = "ftp -n << EOF \n" +
                "open 192.168.1.200 \n" +
                "user jfftp 1Qaz#edc \n" +
                "bin \n" +
                "lcd /home/yh/soft \n" +
                "cd /home/jfftp/deploy/2018-02-01\n" +
                "get 12.zip\n" +
                "bye\n" +
                "EOF\n";
        long start = System.currentTimeMillis();
        sshExecutor.execute(cmd);
        long end  = System.currentTimeMillis();
        System.out.println("共耗时: " + (end-start)/1000+"秒");

        Vector<String> stdout = sshExecutor.getStandardOutput();
        for (String str : stdout) {
            System.out.println(str);
        }
    }
}
