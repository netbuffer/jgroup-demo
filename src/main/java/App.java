import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

import java.util.Scanner;

@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
        JChannel channel = new JChannel("udp.xml");
        channel.setReceiver(new ReceiverAdapter() {
            @Override
            public void receive(Message msg) {
                log.info("received msg from " + msg.getSrc() + ": " + msg.getObject());
            }
        });
        channel.connect("MyCluster");
        Scanner scanner=new Scanner(System.in);
        while (true){
            String content=scanner.nextLine();
            log.info("input:"+content);
            if(content.equals("exit")){
                break;
            }
            channel.send(new Message(null, content));
        }
        channel.close();
    }
}
