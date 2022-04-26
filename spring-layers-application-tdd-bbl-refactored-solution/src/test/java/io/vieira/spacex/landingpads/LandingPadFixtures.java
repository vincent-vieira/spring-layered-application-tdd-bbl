package io.vieira.spacex.landingpads;

import io.vieira.spacex.landingpads.data.RSpaceXLandingPad;
import io.vieira.spacex.landingpads.web.LandingPadResponse;

public class LandingPadFixtures {

    public static final RSpaceXLandingPad sampleRSpaceXLandingPad = new RSpaceXLandingPad(
            "name",
            "fullName",
            RSpaceXLandingPad.Status.RETIRED,
            "type",
            "locality",
            "region",
            "details",
            "id",
            4d,
            5d
    );

    public static final LandingPad sampleLandingPad = new LandingPad(
            "name",
            "fullName",
            "type",
            "details",
            "id",
            false,
            new LandingPad.Location(
                    "locality",
                    "region",
                    4d,
                    5d
            )
    );

    public static final LandingPadResponse sampleLandingPadResponse = new LandingPadResponse(
            "name",
            "fullName",
            "type",
            "details",
            "id",
            false,
            new LandingPad.Location(
                    "locality",
                    "region",
                    4d,
                    5d
            )
    );
}
