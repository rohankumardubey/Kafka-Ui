package com.provectus.kafka.ui.controller;

import com.provectus.kafka.ui.api.KafkaConnectApi;
import com.provectus.kafka.ui.model.Connect;
import com.provectus.kafka.ui.model.Connector;
import com.provectus.kafka.ui.model.ConnectorAction;
import com.provectus.kafka.ui.model.ConnectorPlugin;
import com.provectus.kafka.ui.model.ConnectorPluginConfigValidationResponse;
import com.provectus.kafka.ui.model.FullConnectorInfo;
import com.provectus.kafka.ui.model.NewConnector;
import com.provectus.kafka.ui.model.Task;
import com.provectus.kafka.ui.service.KafkaConnectService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Log4j2
public class KafkaConnectController implements KafkaConnectApi {
  private final KafkaConnectService kafkaConnectService;

  @Override
  public Mono<ResponseEntity<Flux<Connect>>> getConnects(String clusterName,
                                                         ServerWebExchange exchange) {
    return kafkaConnectService.getConnects(clusterName).map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<String>>> getConnectors(String clusterName, String connectName,
                                                          ServerWebExchange exchange) {
    Flux<String> connectors = kafkaConnectService.getConnectors(clusterName, connectName);
    return Mono.just(ResponseEntity.ok(connectors));
  }

  @Override
  public Mono<ResponseEntity<Connector>> createConnector(String clusterName, String connectName,
                                                         @Valid Mono<NewConnector> connector,
                                                         ServerWebExchange exchange) {
    return kafkaConnectService.createConnector(clusterName, connectName, connector)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Connector>> getConnector(String clusterName, String connectName,
                                                      String connectorName,
                                                      ServerWebExchange exchange) {
    return kafkaConnectService.getConnector(clusterName, connectName, connectorName)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteConnector(String clusterName, String connectName,
                                                    String connectorName,
                                                    ServerWebExchange exchange) {
    return kafkaConnectService.deleteConnector(clusterName, connectName, connectorName)
        .map(ResponseEntity::ok);
  }


  @Override
  public Mono<ResponseEntity<Flux<FullConnectorInfo>>> getAllConnectors(
      String clusterName,
      String search,
      ServerWebExchange exchange
  ) {
    return Mono.just(ResponseEntity.ok(kafkaConnectService.getAllConnectors(clusterName, search)));
  }

  @Override
  public Mono<ResponseEntity<Map<String, Object>>> getConnectorConfig(String clusterName,
                                                                      String connectName,
                                                                      String connectorName,
                                                                      ServerWebExchange exchange) {
    return kafkaConnectService.getConnectorConfig(clusterName, connectName, connectorName)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Connector>> setConnectorConfig(String clusterName, String connectName,
                                                            String connectorName,
                                                            @Valid Mono<Object> requestBody,
                                                            ServerWebExchange exchange) {
    return kafkaConnectService
        .setConnectorConfig(clusterName, connectName, connectorName, requestBody)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Void>> updateConnectorState(String clusterName, String connectName,
                                                         String connectorName,
                                                         ConnectorAction action,
                                                         ServerWebExchange exchange) {
    return kafkaConnectService.updateConnectorState(clusterName, connectName, connectorName, action)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<Task>>> getConnectorTasks(String clusterName, String connectName,
                                                            String connectorName,
                                                            ServerWebExchange exchange) {
    return Mono.just(ResponseEntity
        .ok(kafkaConnectService.getConnectorTasks(clusterName, connectName, connectorName)));
  }

  @Override
  public Mono<ResponseEntity<Void>> restartConnectorTask(String clusterName, String connectName,
                                                         String connectorName, Integer taskId,
                                                         ServerWebExchange exchange) {
    return kafkaConnectService.restartConnectorTask(clusterName, connectName, connectorName, taskId)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<Flux<ConnectorPlugin>>> getConnectorPlugins(
      String clusterName, String connectName, ServerWebExchange exchange) {
    return kafkaConnectService.getConnectorPlugins(clusterName, connectName)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<ConnectorPluginConfigValidationResponse>>
      validateConnectorPluginConfig(
        String clusterName, String connectName, String pluginName, @Valid Mono<Object> requestBody,
        ServerWebExchange exchange) {
    return kafkaConnectService
        .validateConnectorPluginConfig(clusterName, connectName, pluginName, requestBody)
        .map(ResponseEntity::ok);
  }
}
