package riyafa;



import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

/**
 * This class is an example of JMS Client. Using sendMsg() method you can send a message to the IBM MQ
 *
 * To run this example you need com.ibm.mq.jar
 *
 * @author Kaushik.Jana
 * @version 1.0
 */
public class SimpleJMSProducer
{
    public void sendMsg(String msg)
    {
        MQQueueManager queueManager = null;
        MQMessage mqMessage = null;
        MQPutMessageOptions myPMO = null;

        try
        {

            // creating queue manager
            queueManager = new MQQueueManager("QMGR1");

            // creating queue`

            MQQueue queue = queueManager.accessQueue("MQ.REQUEST", CMQC.MQOO_OUTPUT);

            // configuring message encoding, character set, and format

            mqMessage = new MQMessage();
            mqMessage.encoding = new Integer("546");
            mqMessage.characterSet = new Integer("1208");
            mqMessage.format = "MQSTR";

            // sending message

            mqMessage.writeString(msg);
            myPMO = new MQPutMessageOptions();
            queue.put(mqMessage, myPMO);
            
            // Closing Queue after putting message
            queue.close();

        } catch (Exception je)
        {
            je.printStackTrace();
        } finally
        {
            if (queueManager != null) try
            {
                // Disconnecting queue manager
                queueManager.disconnect();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new SimpleJMSProducer().sendMsg("Hi Kausik ... How are you ?");
    }
}