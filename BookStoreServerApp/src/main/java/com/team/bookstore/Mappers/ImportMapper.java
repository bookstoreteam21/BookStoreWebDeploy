package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.ImportDetailRequest;
import com.team.bookstore.Dtos.Requests.ImportRequest;
import com.team.bookstore.Dtos.Responses.ImportDetailResponse;
import com.team.bookstore.Dtos.Responses.ImportResponse;
import com.team.bookstore.Entities.Book;
import com.team.bookstore.Entities.Import;
import com.team.bookstore.Entities.Import_Detail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ImportMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "import_detail",source = "importDetailRequests",
            qualifiedByName = "toImport_detail")
    @Mapping(target = "import_total",ignore = true)
    @Mapping(target = "import_status",ignore = true)
    Import toImport(ImportRequest importRequest);
    @Named("toImport_detail")
    default Set<Import_Detail> toImport_detail(List<ImportDetailRequest> importDetailRequests){
        Set<Import_Detail> importDetails = new HashSet<>();
        importDetailRequests.forEach(importDetailRequest -> {
            Import_Detail importDetail = new Import_Detail();
            Book          book         = new Book();
            book.setId(importDetailRequest.getBook_id());
            importDetail.setBook(book);
            importDetail.setImport_cost(importDetailRequest.getImport_cost());
            importDetail.setQuantity(importDetailRequest.getQuantity());
            importDetails.add(importDetail);
        });
        return importDetails;
    }
    @Mapping(target = "importDetailResponse",source = "import_detail",
            qualifiedByName = "toImportDetailResponse")
    ImportResponse toImportResponse(Import _import);
    @Named("toImportDetailResponse")
    default List<ImportDetailResponse> toImportDetailResponse(Set<Import_Detail>importDetails){
        List<ImportDetailResponse> importDetailResponses = new ArrayList<>();
        importDetails.forEach(import_detail -> {
            ImportDetailResponse importDetailResponse =
                    new ImportDetailResponse();
            importDetailResponse.setTitle(import_detail.getBook().getTitle());
            importDetailResponse.setQuantity(import_detail.getQuantity());
            importDetailResponse.setImport_cost(import_detail.getImport_cost());
            importDetailResponse.setTotal_cost(import_detail.getTotal_import_cost());
            importDetailResponses.add(importDetailResponse);
        });
        return importDetailResponses;
    }
}

