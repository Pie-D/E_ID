package vn.cmcati.eid.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.stereotype.Service;
import vn.cmcati.eid.dto.request.AuthenticationRequest;
import vn.cmcati.eid.dto.request.IntrospectRequest;
import vn.cmcati.eid.dto.response.AuthenticationResponse;
import vn.cmcati.eid.dto.response.IntrospectResponse;

import java.text.ParseException;

@Service
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}
