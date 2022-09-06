package org.apache.camel.component.xchange;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.camel.CamelContext;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

//@EnabledIfSystemProperty(named = "enable.xchange.itests", matches = "true", disabledReason = "Requires API credentials")
public abstract class XChangeTestSupport extends CamelTestSupport {
    public static WireMockServer wireMockServer = new WireMockServer(9090);



    @BeforeAll
    public static void startWireMockServer() {
        wireMockServer.start();
    }

    @AfterAll
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        addXChangeClient(context);
        return context;
    }


    protected void addXChangeClient(CamelContext context) {

        XChangeComponent xChangeComponent = new XChangeComponent();

        Class<? extends Exchange> exchangeClass = XChangeHelper.loadXChangeClass(context, "binance");
        ExchangeSpecification specification = new ExchangeSpecification(exchangeClass);
        specification.setSslUri("http://localhost:9090");

        XChange xchange = new XChange(ExchangeFactory.INSTANCE.createExchange(specification));
        xChangeComponent.putXChange("binance", xchange);

        context.addComponent("xchange", xChangeComponent);
    }
}
