package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import br.com.rhribeiro25.baseprojectspringwebflux.core.useCases.UserService;
import br.com.rhribeiro25.baseprojectspringwebflux.utils.JwtUtils;
import br.com.rhribeiro25.baseprojectspringwebflux.utils.RolesUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AuthorizationConfig implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private static final String TOKEN_AUTHORIZATION = "Authorization";
    private Set<String> permitAll = new HashSet<>();
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public AuthorizationConfig() {
        permitAll.add("/");
        permitAll.add("/error");
        permitAll.add("/favicon.ico");
        permitAll.add("/**/v3/api-docs/**");
        permitAll.add("/**/swagger-resources/**");
        permitAll.add("/webjars/**");
        permitAll.add("/doc.html");
        permitAll.add("/swagger-ui.html");
        permitAll.add("/**/oauth/**");
        permitAll.add("/configuration/**");
        permitAll.add("/swagger*/**");
        permitAll.add("/build/**");
        permitAll.add("/actuator/**");
        permitAll.add("/api/auth/login");
        permitAll.add("/api/auth/refresh");
        permitAll.add("/api/auth/logout");
    }

    @SneakyThrows
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        String methodValue = authorizationContext.getExchange().getRequest().getMethodValue();
        String requestPath = authorizationContext.getExchange().getRequest().getURI().getPath();

        if (permitAll(requestPath)) {
            Mono<AuthorizationDecision> auth = Mono.just(new AuthorizationDecision(true));
            return auth;
        }

        String authToken = authorizationContext.getExchange().getRequest().getHeaders().getFirst(TOKEN_AUTHORIZATION);
        if (authToken == null) {
            Mono<AuthorizationDecision> auth = Mono.just(new AuthorizationDecision(false));
            return auth;
        }

        return Mono.justOrEmpty(getAuthorizationDecisionMono(authorizationContext, methodValue, authToken))
                .map(result -> new AuthorizationDecision(result)).onErrorReturn(new AuthorizationDecision(false));
    }

    private Boolean getAuthorizationDecisionMono(AuthorizationContext authorizationContext, String requestPath, String authToken) throws JwtException, JsonProcessingException {
//      ObjectResponse response = userService.getPermission(authToken);
//      RoleResponse role = new RoleResponse();
//        role.setRoleType(((LinkedHashMap) response.getData()).get("roleType").toString());
//        if (response == null || role.getRoleType() == null ) return false;
//        if(!requestPath.contains(HttpMethod.GET.name()))
//             return checkAuthoritiesWriters(role.getRoleType(), requestPath);
//        else if(requestPath.contains(HttpMethod.GET.name()))
//            return checkAuthoritiesViewers(role.getRoleType(), requestPath);
        return false;
    }

    private boolean permitAll(String requestPath) {
        return permitAll.stream()
                .filter(r -> antPathMatcher.match(r, requestPath)).findFirst().isPresent();
    }

    private boolean checkAuthoritiesViewers(String role, String requestPath) {
        if (Arrays.stream(RolesUtils.VIEWERS).filter(x -> x.equals(role)).findFirst().isPresent()) {
            return HttpMethod.GET.name().contains(requestPath);
        }
        return true;
    }

    private boolean checkAuthoritiesWriters(String role, String requestPath) {
        if (Arrays.stream(RolesUtils.WRITERS).filter(x -> x.equals(role)).findFirst().isPresent()) {
            return true;
        }
        return false;
    }
}
