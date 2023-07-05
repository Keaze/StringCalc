package org.example;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Result<R, E> {

    static <A, B> Result<List<A>, List<B>> traverse(Result<? extends Collection<Result<A, B>>, B> xs) {
        if (!xs.successful()) {
            return Result.failure(Collections.singletonList(xs.error()));
        }
        final boolean failure = xs.getOrThrow().stream().anyMatch(x -> !x.successful());
        if (failure) {
            return Result.failure(xs.getOrThrow().stream().filter(x -> !x.successful()).map(Result::error).collect(Collectors.toList()));
        }
        return Result.success(xs.getOrThrow().stream().map(Result::getOrThrow).collect(Collectors.toList()));
    }

    static <V, U> Result<V, U> failure(Failure<V, U> failure) {
        Objects.requireNonNull(failure, "Failure should not be null");
        return new Failure<>(failure.error);
    }

    static <V, U> Result<V, U> failure(U message) {
        return new Failure<>(message);
    }

    static <V, G> Result<V, G> success(V value) {
        return new Success<>(value);
    }

    default R getOrElse(final R defaultValue) {
        if (this.successful()) {
            return this.getOrThrow();
        } else {
            return defaultValue;
        }
    }

    default Result<R, E> or(Result<R, E> other) {
        Objects.requireNonNull(other, "Other should not be null");
        if (this.successful()) {
            return this;
        } else {
            return other;
        }
    }

    default R getOrElse(final Supplier<R> defaultValue) {
        if (this.successful()) {
            return this.getOrThrow();
        } else {
            return defaultValue.get();
        }
    }

    R getOrThrow(Supplier<RuntimeException> e);

    default R getOrThrow() {
        return getOrThrow(() -> new IllegalStateException("Not successful"));
    }

    default <U> Result<U, E> map(Function<R, U> f) {
        Objects.requireNonNull(f, "Mapper should not be null");
        return this.flatMap(x -> success(f.apply(x)));
    }

    default <U> Result<R, U> mapError(Function<E, U> f) {
        Objects.requireNonNull(f, "Mapper should not be null");
        if (successful()) {
            return success(this.getOrThrow());
        } else {
            return failure(f.apply(this.error()));
        }
    }

    <U> Result<U, E> flatMap(Function<R, Result<U, E>> f);

    default Result<R, E> filter(Predicate<R> f, E error) {
        Objects.requireNonNull(f, "Predicate should not be null");
        return this.flatMap(x -> {
            if (f.test(x)) {
                return this;
            } else {
                return failure(error);
            }
        });
    }

    E error();

    boolean successful();

    void ifSuccess(Consumer<R> f);

    default <V, G> Result<V, List<E>> map2(Result<G, E> o, BiFunction<R, G, V> f) {
        return this.mapError(Arrays::asList).map2(o.mapError(Arrays::asList), f, (x, y) -> Stream.concat(x.stream(), y.stream()).collect(Collectors.toList()));
    }

    default <V, G> Result<V, E> map2(Result<G, E> o, BiFunction<R, G, V> f, BinaryOperator<E> errorMapper) {
        Objects.requireNonNull(o, "Other should not be null");
        Objects.requireNonNull(f, "Mapper should not be null");
        Objects.requireNonNull(errorMapper, "error should not be null");

        if (!this.successful() && !o.successful()) {
            return failure(errorMapper.apply(this.error(), o.error()));
        }
        if (!this.successful()) {
            return failure(this.error());
        }
        if (!o.successful()) {
            return failure(o.error());
        }

        return success(f.apply(this.getOrThrow(), o.getOrThrow()));
    }

    class Failure<R, E> implements Result<R, E> {

        private final E error;

        private Failure(E message) {
            this.error = message;
        }

        @Override
        public String toString() {
            return String.format("Failure(%s)", error);
        }


        @Override
        public R getOrThrow(Supplier<RuntimeException> e) {
            throw e.get();
        }

        @Override
        public <U> Result<U, E> flatMap(Function<R, Result<U, E>> f) {
            return failure(error);
        }

        @Override
        public E error() {
            return error;
        }

        @Override
        public boolean successful() {
            return false;
        }

        @Override
        public void ifSuccess(Consumer<R> f) {
            // Failure -> Do nothing
        }

    }

    class Success<R, E> implements Result<R, E> {

        private final R value;

        private Success(R value) {
            super();
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Success(%s)", value.toString());
        }

        @Override
        public R getOrThrow(Supplier<RuntimeException> e) {
            return value;
        }

        @Override
        public <U> Result<U, E> flatMap(Function<R, Result<U, E>> f) {
            return f.apply(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Success<?, ?> success)) return false;

            return Objects.equals(value, success.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public E error() {
            throw new IllegalStateException("No Error");
        }

        @Override
        public boolean successful() {
            return true;
        }

        @Override
        public void ifSuccess(Consumer<R> f) {
            f.accept(value);
        }
    }
}
