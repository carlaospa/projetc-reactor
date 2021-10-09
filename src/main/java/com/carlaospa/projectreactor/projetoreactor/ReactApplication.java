package com.carlaospa.projectreactor.projetoreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.util.Arrays.asList;

@Slf4j
public class ReactApplication {
    public static void main(String[] args) {

        System.out.println("Mono");
        Mono.just("Hello Word").
                subscribe(log::info);

        System.out.println("just -> 1 até 5");
        Flux.just(1, 2, 3, 4, 5)
                .subscribe(i -> log.info("num: {}", i));

        System.out.println("range -> 1 até 5");
        Flux.range(1, 5)
                //.repeat()
                .subscribe(i -> log.info("num: {}", i));

        System.out.println("Converter de Flux Para Mono");
        Flux.from(Mono.just("Hello Word")).subscribe(log::info);

        System.out.println("Converter de Mono Para Flux");
        Mono.from(Flux.range(1,5).repeat())
                .subscribe(y -> log.info("num: {}", y));

        System.out.println("Array");
        Flux.fromArray(new Integer[] {1, 2, 3, 4, 5})
                .subscribe(i -> log.info("num: {}", i));

        System.out.println("Itarable");
        Flux.fromIterable(asList(1, 2, 3, 4, 5))
                .subscribe(i -> log.info("num: {}", i));

        System.out.println("justOrEmpty");
        Flux.from(Mono.justOrEmpty(Optional.ofNullable("Olá")))
                .subscribe(e -> log.info("just or empty: {}", e));

        System.out.println("exemplo defer");
        boolean somentePares = false;
        Flux.defer(() -> somentePares ?
                Flux.range(1, 10).filter(p -> p % 2 == 0):
                Flux.range(1, 10).filter(p -> p % 2 != 0))
                .subscribe(
                        e -> log.info("elem: {}", e),
                        error -> log.error("erro" + error),
                        () -> log.info("complete"),
                        subscriptor -> {
                            subscriptor.request(4);
                            subscriptor.cancel();
                        });


    }
}
