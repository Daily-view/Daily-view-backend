package com.dailyview.api.fetcher

import com.dailyview.api.generated.DgsConstants
import com.dailyview.api.generated.types.AuthToken
import com.dailyview.api.generated.types.CreateMemberInput
import com.dailyview.api.generated.types.LoginInput
import com.dailyview.api.service.member.MemberAuthService
import com.dailyview.api.service.member.MemberService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class MemberMutationFetcher(
    private val memberService: MemberService,
    private val memberAuthService: MemberAuthService
) {

    @DgsMutation(field = DgsConstants.MUTATION.CreateMember)
    fun signup(@InputArgument input: CreateMemberInput): Boolean {
        return memberService.signup(input)
    }

    @DgsMutation(field = DgsConstants.MUTATION.Login)
    fun login(@InputArgument input: LoginInput): AuthToken {
        return memberAuthService.login(input)
    }
}
