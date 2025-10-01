package org.apache.log4j.p007or.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.p007or.ObjectRenderer;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/apache/log4j/or/jms/MessageRenderer.class */
public class MessageRenderer implements ObjectRenderer {
    @Override // org.apache.log4j.p007or.ObjectRenderer
    public String doRender(Object obj) {
        if (obj instanceof Message) {
            StringBuffer stringBuffer = new StringBuffer();
            Message message = (Message) obj;
            try {
                stringBuffer.append("DeliveryMode=");
                switch (message.getJMSDeliveryMode()) {
                    case 1:
                        stringBuffer.append("NON_PERSISTENT");
                        break;
                    case 2:
                        stringBuffer.append("PERSISTENT");
                        break;
                    default:
                        stringBuffer.append(Constants.SIDE_UNKNOWN);
                        break;
                }
                stringBuffer.append(", CorrelationID=");
                stringBuffer.append(message.getJMSCorrelationID());
                stringBuffer.append(", Destination=");
                stringBuffer.append(message.getJMSDestination());
                stringBuffer.append(", Expiration=");
                stringBuffer.append(message.getJMSExpiration());
                stringBuffer.append(", MessageID=");
                stringBuffer.append(message.getJMSMessageID());
                stringBuffer.append(", Priority=");
                stringBuffer.append(message.getJMSPriority());
                stringBuffer.append(", Redelivered=");
                stringBuffer.append(message.getJMSRedelivered());
                stringBuffer.append(", ReplyTo=");
                stringBuffer.append(message.getJMSReplyTo());
                stringBuffer.append(", Timestamp=");
                stringBuffer.append(message.getJMSTimestamp());
                stringBuffer.append(", Type=");
                stringBuffer.append(message.getJMSType());
            } catch (JMSException e) {
                LogLog.error("Could not parse Message.", e);
            }
            return stringBuffer.toString();
        }
        return obj.toString();
    }
}
