package com.saas.notificationservice.grpc;

import com.saas.notificationservice.dto.SubscriberPreferencesResponse;
import com.saas.notificationservice.proto.*;
import com.saas.notificationservice.service.SubscriberPreferencesService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class SubscriberPreferencesGrpcService extends SubscriberPreferencesServiceGrpc.SubscriberPreferencesServiceImplBase {

    private final SubscriberPreferencesService service;
    private final ProtoConverter converter;

    @Override
    public void createPreferences(CreatePreferencesRequest request, StreamObserver<PreferencesResponse> responseObserver) {
        try {
            SubscriberPreferencesResponse dto = service.create(converter.toPreferencesRequest(request));
            responseObserver.onNext(converter.toPreferencesProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getPreferences(GetPreferencesRequest request, StreamObserver<PreferencesResponse> responseObserver) {
        try {
            SubscriberPreferencesResponse dto = service.getById(request.getId());
            responseObserver.onNext(converter.toPreferencesProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getPreferencesByCustomerId(GetPreferencesByCustomerIdRequest request, StreamObserver<PreferencesResponse> responseObserver) {
        try {
            SubscriberPreferencesResponse dto = service.getByCustomerId(request.getCustomerId());
            responseObserver.onNext(converter.toPreferencesProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAllPreferences(GetAllPreferencesRequest request, StreamObserver<PreferencesListResponse> responseObserver) {
        try {
            List<SubscriberPreferencesResponse> dtos = service.getAll();
            PreferencesListResponse.Builder builder = PreferencesListResponse.newBuilder();
            dtos.forEach(dto -> builder.addPreferences(converter.toPreferencesProto(dto)));
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void updatePreferences(UpdatePreferencesRequest request, StreamObserver<PreferencesResponse> responseObserver) {
        try {
            SubscriberPreferencesResponse dto = service.update(request.getId(), converter.toPreferencesRequest(request));
            responseObserver.onNext(converter.toPreferencesProto(dto));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void deletePreferences(DeletePreferencesRequest request, StreamObserver<DeletePreferencesResponse> responseObserver) {
        try {
            service.delete(request.getId());
            responseObserver.onNext(DeletePreferencesResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Preferences deleted successfully")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}

