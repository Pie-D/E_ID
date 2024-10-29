package vn.cmcati.eid.processer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.cmcati.eid.config.Environment;
import vn.cmcati.eid.config.PartnerInfo;
import vn.cmcati.eid.exception.MoMoException;
import vn.cmcati.eid.utils.Execute;

public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}