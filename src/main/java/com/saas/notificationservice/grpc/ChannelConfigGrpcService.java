package com.saas.notificationservice.grpc;

import com.saas.notificationservice.proto.*;
import com.saas.notificationservice.service.ChannelConfigService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class ChannelConfigGrpcService extends ChannelConfigServiceGrpc.ChannelConfigServiceImplBase {

    private final ChannelConfigService service;
    private final ProtoConverter converter;

    @Override
    public void createChannelConfig(CreateChannelConfigRequest request, StreamObserver<com.saas.notificationservice.proto.ChannelConfigResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.ChannelConfigResponse dto = service.create(converter.toChannelConfigRequest(request));
            responseObserver.onNext(converter.toChannelConfigProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getChannelConfig(GetChannelConfigRequest request, StreamObserver<com.saas.notificationservice.proto.ChannelConfigResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.ChannelConfigResponse dto = service.getById(request.getId());
            responseObserver.onNext(converter.toChannelConfigProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAllChannelConfigs(GetAllChannelConfigsRequest request, StreamObserver<ChannelConfigListResponse> responseObserver) {
        try {
            List<com.saas.notificationservice.dto.ChannelConfigResponse> dtos = service.getAll();
            ChannelConfigListResponse.Builder builder = ChannelConfigListResponse.newBuilder();
            dtos.forEach(dto -> builder.addConfigs(converter.toChannelConfigProto(dto)));
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void updateChannelConfig(UpdateChannelConfigRequest request, StreamObserver<com.saas.notificationservice.proto.ChannelConfigResponse> responseObserver) {
        try {
            com.saas.notificationservice.dto.ChannelConfigResponse dto = service.update(request.getId(), converter.toChannelConfigRequest(request));
            responseObserver.onNext(converter.toChannelConfigProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deleteChannelConfig(DeleteChannelConfigRequest request, StreamObserver<DeleteChannelConfigResponse> responseObserver) {
        try {
            service.delete(request.getId());
            responseObserver.onNext(DeleteChannelConfigResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Channel config deleted successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}

