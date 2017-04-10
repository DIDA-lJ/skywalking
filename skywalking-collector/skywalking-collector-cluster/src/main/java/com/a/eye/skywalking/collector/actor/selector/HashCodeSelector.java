package com.a.eye.skywalking.collector.actor.selector;

import com.a.eye.skywalking.collector.actor.AbstractWorker;
import com.a.eye.skywalking.collector.actor.WorkerRef;

import java.util.List;

/**
 * The <code>HashCodeSelector</code> is a simple implementation of {@link WorkerSelector}.
 * It choose {@link WorkerRef} by message {@link AbstractHashMessage} key's hashcode, so it can use to send the same hashcode
 * message to same {@link WorkerRef}. Usually, use to database operate which avoid dirty data.
 *
 * @author pengys5
 * @since feature3.0
 */
public class HashCodeSelector implements WorkerSelector<WorkerRef> {

    /**
     * Use message hashcode to select {@link WorkerRef}.
     *
     * @param members given {@link WorkerRef} list, which size is greater than 0;
     * @param message the {@link AbstractWorker} is going to send.
     * @return the selected {@link WorkerRef}
     */
    @Override
    public WorkerRef select(List<WorkerRef> members, Object message) {
        if (message instanceof AbstractHashMessage) {
            AbstractHashMessage hashMessage = (AbstractHashMessage) message;
            int size = members.size();
            int selectIndex = Math.abs(hashMessage.getHashCode()) % size;
            return members.get(selectIndex);
        } else {
            throw new IllegalArgumentException("the message send into HashCodeSelector must implementation of AbstractHashMessage");
        }
    }
}
