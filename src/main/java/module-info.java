module vicuna.infra.quarkus {
    requires vicuna.core.lib;
    requires java.persistence;
    requires java.transaction;
    requires lombok;
    requires jakarta.inject.api;
    requires jakarta.enterprise.cdi.api;
    requires quarkus.hibernate.orm;
}