package dogapi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String breed = "hound";
        BreedFetcher fetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());

        int result = getNumberOfSubBreeds(breed, fetcher);
        System.out.println(breed + " has " + result + " sub breeds");

        breed = "cat";
        result = getNumberOfSubBreeds(breed, fetcher);
        System.out.println(breed + " has " + result + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher fetcher) {
        if (breed == null) throw new IllegalArgumentException("breed must not be null");
        if (fetcher == null) throw new IllegalArgumentException("breedFetcher must not be null");
        try {
            return fetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }



}package dogapi;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String breed = "hound";
        BreedFetcher fetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());

        int result = getNumberOfSubBreeds(breed, fetcher);
        System.out.println(breed + " has " + result + " sub breeds");

        breed = "cat";
        result = getNumberOfSubBreeds(breed, fetcher);
        System.out.println(breed + " has " + result + " sub breeds");
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the
     * provided fetcher.
     * @param breed the name of the dog breed
     * @param breedFetcher the breedFetcher to use
     * @return the number of sub breeds. Zero should be returned if there are no sub breeds
     * returned by the fetcher
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher fetcher) {
        if (breed == null) throw new IllegalArgumentException("breed must not be null");
        if (fetcher == null) throw new IllegalArgumentException("breedFetcher must not be null");
        try {
            return fetcher.getSubBreeds(breed).size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }



}