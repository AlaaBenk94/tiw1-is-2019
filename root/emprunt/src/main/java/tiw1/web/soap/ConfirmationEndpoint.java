package tiw1.web.soap;

import fr.univ_lyon1.tiw1_is.confirmation.service.ConfirmationRequest;
import fr.univ_lyon1.tiw1_is.confirmation.service.ConfirmationResponse;
import fr.univ_lyon1.tiw1_is.confirmation.service.ObjectFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import tiw1.service.ConfirmationService;

import static tiw1.web.soap.SoapEndpointConfig.NAMESPACE_URI;

@Endpoint
public class ConfirmationEndpoint {
    private final static ObjectFactory confirmationObjectFactory = new ObjectFactory();

    private final ConfirmationService confirmationService;

    public ConfirmationEndpoint(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "confirmationRequest")
    @ResponsePayload
    public ConfirmationResponse transfert(@RequestPayload ConfirmationRequest confirmationRequest) {
        return confirmationService.activateEmprunt(confirmationRequest.getActivationNumber());
    }
}
