package riyafa;



import com.ibm.mq.MQException;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import java.io.IOException;

/**
 * This class is an example of JMS Client. Using sendMsg() method you can send a message to the IBM MQ
 *
 * To run this example you need com.ibm.mq.jar
 *
 * @author Riyafa Abdul Hameed
 * @version 1.0
 */
public class SimpleJMSConsumer
{
    public String receiveMsg()
    {
        MQQueueManager queueManager = null;
        MQMessage mqMessage = null;

        try
        {

            // creating queue manager
            queueManager = new MQQueueManager("QMGR1");

            // creating queue`

            MQQueue queue = queueManager.accessQueue("MQ.REQUEST", CMQC.MQOO_INPUT_SHARED);

            // configuring message encoding, character set, and format

            mqMessage = new MQMessage();
            
            // receiving message

            queue.get(mqMessage);
            // Closing Queue after putting message
            queue.close();
           return mqMessage.readStringOfByteLength(mqMessage.getMessageLength());
            

        } catch (MQException | IOException je)
        {
            System.err.println(je.getMessage());
        } finally
        {
            if (queueManager != null) try
            {
                // Disconnecting queue manager
                queueManager.disconnect();
            } catch (Exception e)
            {
                        System.err.println(e.getMessage());
            }
        }
        return "";
    }

    public static void main(String[] args)
    {
        System.out.println(new SimpleJMSConsumer().receiveMsg());
    }
}
