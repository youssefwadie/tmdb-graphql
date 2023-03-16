package com.github.youssefwadie.themoviedbgraphql.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class SearchBag {
    private final String keyword;
    private final Integer page;
    private final Boolean includeAdult;
    private final String region;
    private final Integer year;
    private final Integer primaryReleaseYear;

    private SearchBag(String keyword, Integer page,
                      Boolean includeAdult,
                      String region, Integer year,
                      Integer primaryReleaseYear) {
        this.keyword = keyword;
        this.page = page;
        this.includeAdult = includeAdult;
        this.region = region;
        this.year = year;
        this.primaryReleaseYear = primaryReleaseYear;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchBag{");
        sb.append("keyword='").append(keyword).append('\'');
        if (page != null) sb.append(", page=").append(page);
        if (includeAdult != null) sb.append(", includeAdult=").append(includeAdult);
        if (region != null) sb.append(", region='").append(region).append('\'');
        if (year != null) sb.append(", year=").append(year);
        if (primaryReleaseYear != null) sb.append(", primaryReleaseYear=").append(primaryReleaseYear);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder(String keyword) {
        return new Builder(keyword);
    }


    public static class Builder {
        private final String keyword;
        private Integer page;
        private Boolean includeAdult;
        private String region;
        private Integer year;
        private Integer primaryReleaseYear;

        private Builder(String keyword) {
            Objects.requireNonNull(keyword);
            this.keyword = keyword;
        }

        public Builder page(Integer page) {
            Objects.requireNonNull(page);
            this.page = page;
            return this;
        }

        public Builder includeAdult(Boolean includeAdult) {
            Objects.requireNonNull(includeAdult);
            this.includeAdult = includeAdult;
            return this;
        }

        public Builder region(String region) {
            Objects.requireNonNull(region);
            this.region = region;
            return this;
        }

        public Builder year(Integer year) {
            Objects.requireNonNull(year);
            this.year = year;
            return this;
        }

        public Builder primaryReleaseYear(Integer primaryReleaseYear) {
            Objects.requireNonNull(primaryReleaseYear);
            this.primaryReleaseYear = primaryReleaseYear;
            return this;
        }

        public SearchBag build() {
            return new SearchBag(keyword, page, includeAdult,
                    region, year, primaryReleaseYear);
        }
    }


}
