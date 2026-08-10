package br.dev.allissonnunes.algashop.billing.application.security;

import java.util.UUID;

public interface SecurityCheckApplicationService {

    UUID getAuthenticatedUserId();

    boolean isAuthenticated();

    boolean isMachineAuthentication();

}
