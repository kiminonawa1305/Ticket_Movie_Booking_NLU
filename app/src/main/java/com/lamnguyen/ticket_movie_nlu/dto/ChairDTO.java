package com.lamnguyen.ticket_movie_nlu.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.enums.ChairType;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChairDTO implements Serializable {
    private Integer id, userId;
    private String name;
    private ChairStatus status;
    private ChairType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChairDTO chairDTO = (ChairDTO) o;
        return Objects.equals(id, chairDTO.id);
    }
}
