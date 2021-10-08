package julien.hammer.p4lamzone.di;

import julien.hammer.p4lamzone.service.MareuApiService;
import julien.hammer.p4lamzone.service.ProjectMareuApiService;

/**
 * Created by Julien HAMMER - Apprenti Java with openclassrooms on .
 */
public class DI {
    private static MareuApiService service = new ProjectMareuApiService();

    /**
     * Get an instance on @{@link MareuApiService}
     * @return
     */
    public static MareuApiService getMareuApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MareuApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MareuApiService getNewInstanceMareuApiService() {
        return new ProjectMareuApiService();
    }
}
